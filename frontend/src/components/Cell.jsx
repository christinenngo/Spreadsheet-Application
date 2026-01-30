import {memo, useState} from "react";


//useState()
//

// memo()
//  returns a memoized react component, which only re-renders if its props have changed,
//  when parent component re-renders

const Cell = memo(({row, col, cellData, onSave}) => { // re-render
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

        if (isEditing) {
            return (<td className="border border-gray-300 p-0 min-w-[100px] h-[30px]">
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

        return (<td onDoubleClick={handleDoubleClick}
                    className="border border-gray-300 min-w-[100px] h-[30px] px-2 py-1 cursor-text hover:bg-gray-50 relative overflow-hidden whitespace-nowrap">
            {cellData.value}
        </td>)


})

export default Cell;

