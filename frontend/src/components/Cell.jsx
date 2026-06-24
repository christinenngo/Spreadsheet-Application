import {memo, useState} from "react";


//useState()
//

// memo()
//  returns a memoized react component, which only re-renders if its props have changed,
//  when parent component re-renders

const Cell = memo(({row, col, cellData, onSave, onSelect, selectedCell, selectedGroups, referencedCells, error}) => { // re-render
        const [isEditing, setisEditing] = useState(false);
        const [editValue, setEditValue] = useState('');

        const editMode = (node) => {
            if(node){
                node.focus()
            }
        }

        const handleDoubleClick = () => {
            const initialText = cellData.expression || cellData.value;
            setEditValue(initialText);
            setisEditing(true);
        }

        const handleOutBlur = () => {
            setisEditing(false);
            onSave(row, col, editValue);
        }

        const handleKeyEnter = (e) => {
            if (e.key === 'Enter') {
                handleOutBlur();
            }
        }

        const isSelected = selectedCell?.row === row && selectedCell?.col === col;

        const group = selectedGroups.find(g =>
            row >= g.startRow && row <= g.endRow && col >= g.startCol && col <= g.endCol
        )
        let isGroup = !!group;

        const top = isGroup && row === group.startRow;
        const bottom = isGroup && row === group.endRow;
        const left = isGroup && col === group.startCol;
        const right = isGroup && col === group.endCol;

        const cellRef = !isGroup && referencedCells.find(c =>
            c.row === row && c.col === col
        )
        const isReference = !!cellRef;

        const groupStyle = isGroup ?
            `${group.color.bg}
             ${top ?  `border-t-2 [border-top-style:dashed] ${group.color.border}` : "border-t-gray-300"}
             ${bottom ?  `border-b-2 [border-bottom-style:dashed] ${group.color.border}` : "border-b-gray-300"}
             ${left ?  `border-l-2 [border-left-style:dashed] ${group.color.border}` : "border-l-gray-300"}
             ${right ?  `border-r-2 [border-right-style:dashed] ${group.color.border}` : "border-r-gray-300"}` : "";

        const cellRefStyle = isReference ?
            `${cellRef.color.bg} ${cellRef.color.border} border-dashed border-2` : "";

        const errorStyle = "absolute top-0 right-0 z-20 w-0 h-0 border-t-0 border-l-0 border-r-8 border-b-8 border-t-transparent border-b-transparent border-solid border-r-red-500";

        const coloredCell = isSelected || isGroup || isReference;

        if (isEditing) {
            return (
                <td onClick={() => onSelect(row, col, cellData)}
                    className={`border ${coloredCell ? "" : "border-gray-300 hover:bg-gray-50"} p-0 min-w-[100px] h-[30px]
                        ${isSelected ? "border-blue-400 bg-blue-50 border-2" : ""}
                        ${groupStyle}
                        ${cellRefStyle}`}>
                <input
                    ref={editMode}
                    value={editValue}
                    onChange={e => setEditValue(e.target.value)}
                    onBlur={handleOutBlur}
                    onKeyDown={handleKeyEnter}
                    type="text"
                    className="w-full h-full px-2 py-1 outline-none border-2 border-blue-500 z-10 relative"
                />
            </td>)
        }

        return (
            <td onDoubleClick={handleDoubleClick}
                onClick={() => onSelect(row, col, cellData)}
                className={`border ${coloredCell ? "" : "border-gray-300 hover:bg-gray-50"} min-w-[100px] h-[30px] px-2 py-1 cursor-text relative overflow-hidden whitespace-nowrap
                    ${isSelected ? "border-blue-400 bg-blue-50 border-2" : ""}
                    ${groupStyle}
                    ${cellRefStyle}`}>
                {cellData.value}
                {error && (
                    <div
                        title={error}
                        className={`${errorStyle}`}
                    ></div>
                )}
            </td>)
})

export default Cell;

