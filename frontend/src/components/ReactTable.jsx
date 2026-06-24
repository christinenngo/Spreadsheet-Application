import Cell from './Cell';
import {initialGrid, colLabels} from "./CellDefs";
import React, {useCallback, useState, useEffect} from "react";

const ReactSpreadSheet = () => {

    const [data, setData] = useState(initialGrid);
    const [selectedCell, setSelectedCell] = useState(null);
    const [selectedGroups, setSelectedGroups] = useState([]);
    const [expressionBar, setExpressionBar] = useState("");
    const [referencedCells, setReferencedCells] = useState([]);
    const [cellErrors, setCellErrors] = useState({})
    const colors = [
        {border: 'border-pink-500', bg: 'bg-pink-100'},
        {border: 'border-green-500', bg: 'bg-green-100'},
        {border: 'border-red-500', bg: 'bg-red-100'},
        {border: 'border-purple-500', bg: 'bg-purple-100'},
        {border: 'border-teal-500', bg: 'bg-teal-100'},
        {border: 'border-orange-500', bg: 'bg-orange-100'}
    ]

    // fetches the entire spreadsheet
        const fetchSpreadsheet = useCallback(() => {
            fetch("/api/spreadsheet")
            .then((res) => res.json())
            .then((json) => {setData(json);
            console.log("sending data");})
            .catch((err) => console.error("Failed to fetch spreadsheet data:", err));
        }, []);

        useEffect(() => {
            // initial fetch
            fetchSpreadsheet();
        }, [fetchSpreadsheet]);

    const handleSaveCell = async (row, col, cellData, updateSheet = true) => {
        const rowNum = row+1; // 0 counting to 1 counting
        const coord = `${col}${rowNum}`;

        let body;
        if(cellData.startsWith('=')){
            body = { expression: cellData};
        } else {
            if(cellData === ''){body = { value: '' };} // sends a '' to the backend -- used to send 0 before
            else{body = { value: cellData };}
        }

        try{ // post the change for that cell
            const response = await fetch(`/api/cell/${coord}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(body),
            });

            if (!response.ok) { // serverside error
                const errorData = await response.json();
                console.error(`Update failed for ${coord}:`, errorData.error);

                setCellErrors(prev => ({
                    ...prev,
                    [coord]: `Error updating cell ${coord}: ${errorData.error || response.statusText}`
                }))
                return false; // error -> don't refresh data
            }

            setCellErrors(prev => ({
                ...prev,
                [coord]: undefined
            }));

            console.log(`Cell ${coord} updated successfully.`)

            if (updateSheet) {
                fetchSpreadsheet();// no error -> refresh data
            }
        }
        catch (err) {
            console.error("Fetch error during cell update:", err);
            setCellErrors(prev => ({
                ...prev,
                [coord]: "Network error updating cell."
            }))
            return true;
        }
    }

    const handleUndo = async () => {
        try {
            const response = await fetch(`/api/undo`, {
                method: 'POST'
            });

            if (!response.ok) {
                console.error(`Undo failed.`);
                alert(`Error undoing last action.`);
                return;
            }

            console.log(`Successfully undid last action. Refreshing spreadsheet.`)
            fetchSpreadsheet();// no error -> refresh data
        }
        catch (err) {
            console.error("Fetch error during undo:", err);
            alert("Error undoing previous action.");
        }
    }

    const handleRedo = async () => {
        try {
            const response = await fetch(`/api/redo`, {
                method: 'POST'
            });

            if (!response.ok) {
                console.error(`Redo failed.`);
                alert(`Error redoing last action.`);
                return;
            }

            console.log(`Successfully redid last action. Refreshing spreadsheet.`)
            fetchSpreadsheet();// no error -> refresh data
        }
        catch (err) {
            console.error("Fetch error during redo:", err);
            alert("Error redoing previous action.");
        }
    }

    const handleSelectCell = async (row, col, cellData) => {
        setSelectedCell({row, col});

        let expression = "";

        if (cellData.expression !== "") {
            expression = cellData.expression;
            setExpressionBar(expression);
        } else {
            setExpressionBar(cellData.value);
        }

        const references = expression.match(/[A-Z]+\d+:[A-Z]+\d+|[A-Z]+\d+/g) || [];
        const groups = [];
        const cells = [];

        references.forEach((ref, index) => {
            const color = colors[index % colors.length];

            if (ref.includes(':')) {
                groups.push({...findRange(ref), color});
            } else {
                const row = parseInt(ref.match(/\d+/)[0]) - 1;
                const col = ref.match(/[A-Z]+/)[0];

                cells.push({row, col, color})
            }
        })

        setReferencedCells(cells);
        setSelectedGroups(groups);
    }

    const handleNewExpression = () => {
        if (!selectedCell) {
            return;
        }

        handleSaveCell(selectedCell.row, selectedCell.col, expressionBar, true);
    }

    const findRange = (cellGroup) => {
        const [start, end] = cellGroup.split(":");

        const startRow = parseInt(start.match(/\d+/)[0]) - 1;
        const startCol = start.match(/[A-Z]+/)[0];
        const endRow = parseInt(end.match(/\d+/)[0]) - 1;
        const endCol = end.match(/[A-Z]+/)[0];

        return {startRow, startCol, endRow, endCol};
    }

    const downloadFile = (data, fileName, fileType) => {
        const blob = new Blob([data], {type: fileType});

        const a = document.createElement('a');
        a.download = fileName;
        a.href = window.URL.createObjectURL(blob);

        const clickEvent = new MouseEvent('click', {
            view: window,
            bubbles: true,
            cancelable: true,
        })

        a.dispatchEvent(clickEvent);
        a.remove();
    }

    const exportToJson = (e) => {
        e.preventDefault();
        downloadFile(JSON.stringify(data, null, 2), 'spreadsheet-data.json', 'text/json');
    }

    const importJson = async (e) => {
        const file = e.target.files[0];

        try {
            const content = await file.text();
            const rows = JSON.parse(content);

            for (const row of data) {
                const rowIndex = row.id - 1;
                for (const col of colLabels) {
                    const cellData = row[col];
                    if (cellData?.value || cellData?.expression) {
                        await handleSaveCell(rowIndex, col, '', false);
                    }
                }
            }

            for (const row of rows) {
                const rowIndex = row.id - 1;
                for (const col of colLabels) {
                    const cellData = row[col];
                    if (!cellData) {
                        continue;
                    }

                    const value = cellData.expression || cellData.value;
                    if (!value) {
                        continue;
                    }

                    await handleSaveCell(rowIndex, col, value, false);
                }
            }

            fetchSpreadsheet();
        } catch (err) {
            console.error("Failed to import file:", err);
            alert("Invalid file.")
        } finally {
            e.target.value = ''
        }
    }

    return(
        <div className="overflow-auto h-screen w-full bg-white text-sm font-sans">
            <div className="bg-gray-300 px-6 py-3 font-normal text-gray-700 flex gap-3">
                <button className="bg-gray-100 border-2 rounded-lg border-gray-400 px-2 py-1"
                        onClick={handleUndo}>Undo</button>
                <button className="bg-gray-100 border-2 rounded-lg border-gray-400 px-2 py-1"
                        onClick={handleRedo}>Redo</button>

                <div className="px-5 py-1 italic font-medium">fx</div>
                <input className="border border-gray-400 px-2 py-1 flex-1"
                       value={expressionBar}
                       onChange={(e) => {setExpressionBar(e.target.value)}}
                       onKeyPress={(e) => {
                           if (e.key === "Enter") {
                               handleNewExpression()
                           }
                       }}
                />

                <label className="bg-gray-100 border-2 rounded-lg border-gray-400 px-2 py-1 cursor-pointer">
                    Import Data
                    <input
                        className="hidden"
                        type="file"
                        accept="application/json"
                        onChange={importJson}/>
                </label>
                <button className="bg-gray-100 border-2 rounded-lg border-gray-400 px-2 py-1"
                        onClick={exportToJson}>Export Data</button>
            </div>

            <table className="border-collapse w-full">
                <thead>
                <tr>
                    {/* Corner Cell */}
                    <th className="bg-gray-100 border border-gray-300 w-10 sticky left-0 top-0 z-20"></th>
                    { colLabels.map((label) => (
                        <th
                        key={label}
                        className="bg-gray-100 border border-gray-300 px-2 py-1 font-normal text-gray-600 min-w-[100px] sticky top-0 z-10">
                            {label}
                        </th>
                        ))}
                </tr>
                </thead>
                <tbody>
                {data.map((row, rowIndex) => (
                    <tr key={row.id}>
                        {/* Row Number */}
                        <td className="bg-gray-100 border border-gray-300 text-center text-gray-500 w-10 sticky left-0 z-10 select-none">
                            {row.id}
                        </td>
                        {/* Data Cells */}
                        {colLabels.map((colId) => (
                            <Cell
                                key={`${rowIndex}-${colId}`}
                                row={rowIndex}
                                col={colId}
                                cellData={row[colId]}
                                onSave={handleSaveCell}
                                onSelect={handleSelectCell}
                                selectedCell={selectedCell}
                                selectedGroups={selectedGroups}
                                referencedCells={referencedCells}
                                error={cellErrors[`${colId}${rowIndex+1}`]}
                            />
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>)
}

export default ReactSpreadSheet;
