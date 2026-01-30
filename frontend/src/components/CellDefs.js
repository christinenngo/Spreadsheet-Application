
const colLabels = Array.from('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
const MAX_ROWS = 50;

const initialGrid = Array.from({ length: MAX_ROWS }, (_, rowIndex) => {
    const row = {id: rowIndex + 1};
    colLabels.forEach( (char) => {
        row[char] = {value: '', expression: ''};
    })

    return row;

})

export {colLabels, initialGrid}

