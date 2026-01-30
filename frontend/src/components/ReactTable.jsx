import Cell from './Cell';
import {initialGrid, colLabels} from "./CellDefs";
import React, {useCallback, useState, useEffect} from "react";



const ReactSpreadSheet = () => {

    const [data, setData] = useState(initialGrid);

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

    const handleSaveCell = async (row, col, cellData,) => {
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
                alert(`Error updating cell ${coord}: ${errorData.error || response.statusText}`);
                return; // error -> don't refresh data
            }

            console.log(`Cell ${coord} updated successfully. Refreshing spreadsheet.`)
            fetchSpreadsheet();// no error -> refresh data
        }
        catch (err) {
            console.error("Fetch error during cell update:", err);
            alert("Network error updating cell.");
        }
    }

    return(
        <div className="overflow-auto h-screen w-full bg-white text-sm font-sans">
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
                        />
                    ))}
                </tr>
            ))}
            </tbody>
        </table>

    </div>)
}

export default ReactSpreadSheet;
