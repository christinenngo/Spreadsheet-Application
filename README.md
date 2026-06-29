___
### Context
___
A simple spreadsheet application built using Java and React. Uses design patterns to organize code according to object-oriented design principles and make the code flexible, easy to read and maintain.
___
## Additional Independent Study Features:
___
### Undo/Redo:
* Implemented undo/redo functionality using the command pattern.
  * Every time a value or expression is updated, the command stack executes the corresponding command.
  * Added end points for undo/redo.

### Toolbar & Visualizations:
* Added a simple toolbar with buttons for undo/redo, import/export, and an expression editor bar.
* Added color visualizations for cell references and cell groups.
* Added error indicators that showed error messages per cell when hovered over. 

### Import/Export Data:
* Implemented the ability to upload or download spreadsheet data as a JSON file.
  * Import clears the spreadsheet and puts in data from the uploaded file.
