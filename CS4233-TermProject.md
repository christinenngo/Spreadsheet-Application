# CS 4233 - Term Project  - C Term
___
### Context
___
A spreadsheet is a deceptively simple application that combines data representation with computation: grids of cells with either blanks, values, or expressions that evaluate some function over inputs from other cells and/or constants. Fancier spreadsheets can include graphs, images, formatted text, etc. — but we don’t need to consider such details right now.

Spreadsheets are actually a fairly sophisticated functional programming environment: expressions can contain functions of arbitrary number of arguments, or of a range of values; they can contain conditionals to select among options, etc.

In the term project, we will build a simple spreadsheet application using Java and React. We will practice the use of design patterns to organize our code according to object-oriented design principles and make our code flexible, easy to read and maintain.
___
## Spreadsheet Basics:
___
### Cell references:
* In a spreadsheet, cell names are written as `<column name><row index>`, where column names follow the pattern:
  * Columns 1 through 26 get names A through Z, columns 27 through 52 get names AA through AZ, columns 53 through 78 get names BA through BZ ...etc.
  * Row indices are also numbered starting from 1. In this naming scheme, there is no row or column with index 0.
* Spreadsheet cells can be referred individually (e.g. A1, C10, F12, etc.) or as a “cell group” (e.g., A1:C3) which corresponds to a collection of cells on the spreadsheet.
  * A cell group specifies the top left and bottom right corner of the cell collection on the spreadsheet.  For example, “A1:C3” refers to the cell group including A1, A2, A3, B1, B2, B3, C1, C2, C3.
* In the starter code we have provided the `CellCoord` class for you to describe cell coordinates in a spreadsheet.

### Cell Contents:
* An individual spreadsheet cell may:
  * be blank
  * contain a constant  value
  * contain a formula which evaluates to a value.

* The value of a cell has type `CellValue` (provided in the starter code), which may store either a double or string. However, to keep it simple, we will assume that cell values are always doubles in this project.
  * Blank cells are assumed to have a `CellValue` with `null` value.
  * When a constant is entered in a cell, it will store a `CellValue` having that constant double value. 
  * When an expression is entered in a cell, the expression will be evaluated and it will store a `CellValue` having that evaluated value.
* The starter code provides the `Cell` class, representing a cell in the spreadsheet. `Cell` class has private attributes “`value`” and “`expression`” storing the value and expression of a cell. We will explain the expressions further in the [Starter Code section](#starter-code) :
  * if the cell content is blank, its value is assumed to be a `CellValue` object where its `value` attribute is `null`.
  * if the cell content is a constant, its value will be a `CellValue` object storing that constant double and its expression will be `null`.
  * If the cell content is a formula, then its `expression` attribute will store the parsed formula (i.e. `Expression`)  and the `value` attribute will store the `CellValue` object having the result of the expression (i.e., the value it evaluates to).
    * A formula typically starts with `'='` character. It may either involve a constant value (e.g. `“=9”`) or an arithmetic formula containing one or more functions (operators) applied to constant values or references to other cells.
    * A formula that involves a constant just evaluate to that constant value. For example: `“=9”` evaluates to `9.0`.
    * A formula that involves operators and/or cell references will require evaluation of the operators on the provided operands.
      * For example: `"=AVE(B1:B3, SUM(A1:A4)*2, 10+40/8*2)"`
        * The above formula will evaluate to `20.0` (the average of the values in cells `B1`, `B2`, `B3`, "sum of `A1`,`A2`,`A3`, and `A4`”, and the expression `“10+40/8*2”`)
        * No formula is permitted to refer to itself, though, either directly or indirectly, since that would lead to an infinite regress.

### Operators
Your spreadsheet should support the following operators:
#### 1. Arithmetic operators :
*  `*` , `/`, `+`, `-` (assume PEMDAS for precedence, i.e.,  Parentheses, Multiplication and Division, and Addition and Subtraction (from left to right)).
#### 2. Aggregate operators :
* Aggregate operators can have any number of arguments including constants, cell references, and cell group references.  We will support the aggregate functions SUM, COUNT, and AVE:
  * `SUM` : calculates sum of the values of its arguments. Examples:
    * `SUM(10)` evaluates to `10`
    * `SUM(10,5*4)` evaluates to `30`
    * `SUM(A1,A2)` evaluates to `A1+A2`
    * `SUM(A1:B3)` evaluates to `A1+A2+A3+B1+B2+B3`
    * `SUM(A1:B2, A4, 40/2)` evaluates to `A1+A2+B1+B2+A4+20.0`

  * `COUNT` : calculates the number of  cells and values passed to it. Examples:
    * `COUNT(10)` evaluates to 1
    * `COUNT (10,5*4)` evaluates to 2
    * `COUNT (A1,A2)` evaluates to 2
    * `COUNT (A1:B3)` evaluates to 6
    * `COUNT (A1:B2, A4, B2:B4,10)` evaluates to 9

  * `COUNTA` : counts the number of non-empty cells and constants passed to it. (Remember that empty cells are those having `CellValue` with `null` value. ) Examples:
    Assume A2, B1, B2, A4 are non-empty and rest of the cells are empty. 
    * `COUNT (A1,A2)` evaluates to 1
    * `COUNT (A1:B3)` evaluates to 3
    * `COUNT (A1:B3,10)` evaluates to 4
    * `COUNT (A1:B2,A4,B2:B4,10)` evaluates to 6

  * `AVE`: calculates the average of the values of its arguments. Examples:
    * `AVE(10)` evaluates to `1`
    * `AVE(10,5*4)` evaluates to `10`
    * `AVE(A1,A2)` evaluates to `(A1+A2)/2`
    * `AVE(A1:B3)` evaluates to `(A1+A2+A3+B1+B2+B3)/6`
    * `AVE(A1:B2, A4, 40/2)` evaluates to `(A1+A2+B1+B2+A4+20.0)/6`

## Spreadsheet Application
We will build this application progressively using the Model-View-Controller architectural pattern which separates the application's logic from its user interface, enhancing maintainability and testability. The backend (Model and Controller) is implemented as a Java Sprint Boot application, while the frontend (View) is implemented using React.
It consists of three main components: the Model, View, and Controller.
* **Model**: Manages data access (grid and cells) and encapsulates the logic for processing expressions
* **View**: Represents the React user interface (spreadsheet), displaying data and providing interaction.
* **Controller**: Implements Spring Boot entry points, handling all incoming HTTP requests from the React UI. It validates the requests and acts as an intermediary, transforming data between the [Model](#model) and the [View] (#viewmodel) .

[MVC](./images/MVC_UML.jpg)

The starter code provided already implements the View (the React UI) and most of the Controller. Please see the [section "Starter Code"](#starter-code) for more details.
In the term project, we will  mostly work on the model which involves the implementation of:
* cells and cell groups,
* expressions, operators, operands (constants and cell references)
* parsing formulas and converting them to expressions
* evaluating expressions
* connecting Model to the Controller.

You will start with the given code and complete the implementation of the Controller and the Model. You will use several design patterns and make sure that your code is organized according to OOP design principles.
The milestone descriptions will specify the mandatory design patterns you should use; however, you should try to adopt additional design patterns to improve the organization of your code.
___
## Starter code:
___
The starter code provides the IntelliJ Maven project where all dependencies including JUnit libraries are already added to the project configuration (pom.xml file). If you are not familiar with Maven projects, please refer to IntelliJ Maven documentation.

Please refer to the lecture video recording where I provide a walkthrough of the starter code and instructions on how to set it up and run it.
If you edit the `pom.xml` file and edit/add any of the dependencies, don't forget to **"sync"** and **"refresh"** the maven project.

As explained in the previous section, the starter code is organized according to MVC architectural pattern. The UML class diagram for the starter code is given below:

[starter code](images/startcodeUML.png)

### View:
The user interface of the Spreadsheet application is implemented using React. The React application is provided in the `frontend` folder of the starter code. To run the React application:
 * First, install dependencies:
    ```
    npm install
    ```
  * Then run the frontend:
    ```
    npm start
    ```
The basic view implementation of the application is complete and you don’t need to make any changes to the `App.java` and React files under the `frontend` folder.

### Controller:
The controller in this spreadsheet application serves as the bridge between the React frontend (view) and the backend model, implementing the REST API that handles all HTTP requests and responses. It acts as the API gateway and command translator, accepting user requests from the frontend, delegating to the model for business logic, and returning formatted results. It has three primary responsibilities:

1. **HTTP Endpoint Management**
   The `SpreadsheetController` is a Spring REST controller that defines endpoints for:

    * `GET /api/spreadsheet` - Returns the entire 50×26 spreadsheet grid
    * `POST /api/cell/{coord}` - Updates a single cell with either a numeric value or expression

2. **Expression Parsing & Conversion**
   The `ExpressionParser` class handles the complex task of converting user input strings into computable `Expression` objects:

   Uses a 3-step process: 
      ```tokenization → infix-to-postfix conversion (Shunting Yard algorithm) → expression tree construction```.
   
   Parses mathematical expressions with operators `(+, -, ×, ÷)`, aggregate functions `(SUM, AVG, COUNT, COUNTA, MAX)`, and cell references `(A1, B2:D4)`.
   
   Converts raw strings like "=A1+B2*3" into Expression objects that the model can evaluate.

3. **Request/Response Formatting**
   The controller transforms between HTTP JSON and internal model objects:
   * Validates and parses cell coordinates and values
   * Builds JSON representations of cells with their value, expression, and dependencies
   * Provides error handling with appropriate HTTP status codes
   * Ensures CORS support for frontend communication (listening on http://localhost:3000)

**Couple important workflows:**
* *Fetching Spreadsheet Data*: When the React frontend loads or refreshes, it sends an HTTP GET request to the `\spreadsheet` endpoint in the `SpreadsheetController`. The controller retrieves the entire grid of cells from the model and formats them into JSON to send back to the frontend for rendering.

*  *Updating a grid cell's value or expression*: When the value or expression of a spreadsheet cell is updated, the React frontend sends an HTTP POST request to the `\cell` endpoint in the `SpreadsheetController`. The controller processes the request and delegates to the model to update the affected cell. The model then propagates changes through the dependency chain—automatically updating any cells that reference the changed cell, as well as cells that reference those cells, and so on—ensuring all dependent calculations remain current. 
* *Re-fetching Spreadsheet Data*: And when the post request is successful, the frontend will re-fetch the updated spreadheet data. Currently, for simplicity, the controller sends the complete spreadsheet grid back to the frontend after every update. However, this is not efficient. In a real-world application, we would want to optimize this by only sending the changed cells back to the frontend.

The starter code provides the basic implementation of the controller. You will need to complete the implementation of some methods in the `SpreadsheetController` class to connect the controller to the model. Please see the milestone descriptions for more details.

### Model:
The model manages the data  (grid and cells) and encapsulates the logic for processing expressions. In the starter code, the model includes the following classes:

#### Expression Parser

The `ExpressionParser` class provides the static methods for parsing the formula inputs and converting them to `Expression` objects. It uses the **[Shunting Yard Algorithm](https://mathcenter.oxford.emory.edu/site/cs171/shuntingYardAlgorithm/)** for converting the expression from infix notation to postfix notation.
Postfix notation is useful because it allows the expression to compute directly from left to right instead of considering PEMDAS and parenthesis.

* Infix notation:   `(3 + A1) * 20   //  Humans like to evaluate this`
* Postfix notation: `3 A1 + 20 *     // Computers like to evaluate this`

The postfix notation is used to create the Expression Tree (your implementation of `Expression`).

The base shunting yard algorithm allows **Arithmetics functions** (i.e add, subtract, minus, divide), and **Constants**.
You will modify the algorithm to also consider variables such as **cell reference, cell group reference, and aggregate functions** (i.e SUM, COUNT, COUNTA, AVE, etc.).

The flow of the logic includes:
- **Step 1**: Tokenize the string expression to an array of tokens (i.e "A1", "1", "+", "SUM" are tokens).
  - *<--- Already implemented.* See the `tokenize` method in  `ExpressionParser.java`.
- **Step 2**: Convert the array of tokens from infix notation to postfix notation.
  - *<--- Already implemented.* See the `infixToPostfix` method in  `ExpressionParser.java`
- **Step 3**: Convert the array of tokens from postfix notation into an expression tree.
  - *<--- You will work on this in milestones 1 and 2.* See the `postfixToExpression` method in  `ExpressionParser.java`
  - For a high-level description, read the comments in `ExpressionParser.java`


#### Cell Repository
CellRepository is currently configured with static dimensions of 26 columns and 50 rows for simplicity.
CellRepository stores and manages all cell data for the spreadsheet, acting as the central data repository between the View and the Model. It maintains a mapping between each `CellComponent` and its unique `CellCoord`, which represents the cell's position in the grid.
The definition of `CellCoord` class is provided in the starter code.

* Note that `CellCoord` starts at column 0 and row 0, which is different from the basic cell naming convention.
  * For example,
  * Cell `A1` is at `CellCoord` row 0 and column 0.
  * Cell `AA1` is at `CellCoord` row 0 and column 26.
  * Cell `B2` is at `CellCoord` row 1 and column 1.

Cell repository adopts the “Singleton" design pattern. This design allows a single instance of spreadsheet data, meaning `CellRepository` will only get initialized once and stay consistent across all structure levels.
CellRepository can be used as follows:

```
  CellRepository repo = CellRepository.getInstance();
  repo.getReferenceCellComponent(row, col);
  ```
OR
  ```
  CellRepository.getInstance().getReferencedCellComponent(row, col);
  ```

#### Cell
Cell represents a single cell in the spreadsheet.
* Each Cell has a value (initially assumed to be a  `CellValue`  with value `null`).
* Cell holds a reference of its expression, and can be used to re/evaluate and store in value.
  * If the cell is assigned a constant value, its expression will be `null`.
  * Otherwise, if the cell is assigned a formula (e.g. `"=10+A2"`), then the formula will be parsed and converted to an `Expression` object. The `expression` attribute  will hold a reference to that object and the value it evaluates to will be assigned to `value`.

In milestone 2, you will define a class to represent “ cell groups” and you will unify the cell and cell group interfaces by applying composite pattern.

#### Expression
The starter code provides the `Expression` interface. You will implement this interface to create an expression tree structure for representing and evaluating user-entered formulas.

When a user enters a formula in a spreadsheet cell, the `ExpressionParser` converts the formula string into an `Expression` object, which is then used to compute and update the cell's value.
Since the formulas may have nested calls to operators, the best way to represent it will be an expression tree.
You can design your expression structure as you want; however, it should be flexible and easy to extend, i.e., it should be easy to add new operators without major modifications to your code.
You should adopt design patterns and functional programming practices to make your code flexible. We suggest you to adopt "Composite" pattern to unify the interface for operators and operands.

Your implementation of the `Expression` interface should implement the `evaluate()` method.  `evaluate` should evaluate the expression and return the result as a double.

You will work on this part during the first two milestones:
1. In milestone1, you will evaluate expressions where all operands are constants and you will only support arithmetic operators (i.e., +,-,*,/). (For example : `"=5+4*(2+100/(10-2))"`)
2. In milestone2, you will support cell and cell group references. (For example : `"=60+B2+AVE(A1:A2, 10, B2)*SUM(5,6)"`)
  * This will require you allow cell and cell group references as operands in the expression tree.

## Summary of the milestones:
### Milestone1:
* Make sure that you can compile and run the given starter code. Spend some time to review and understand the given code; go back and forth between this document and starter code.
* Implement the expression tree representing the expressions.
  * Your expression tree should implement the interface `Expression` provided in the starter code.
  * Your expression tree should support the arithmetic operators +, -, *, / (i.e., addition, subtraction, multiplication, and division). The operations can be nested. You will add support for aggregate operators (SUM, COUNT, and AVE) in milestone 2.
  * You should implement the `evalaute()` method of the `Expression`; `evaluate` should evaluate the expression and return the result as a `CellValue` having double value.
  * You can assume that all operands are constants (For example : `"=5+4*(2+100/(10-2))"`) You will add support for cell and cell group references in milestone2.
  * Your expression tree structure should comply with OO design principles we discussed in class. You should adopt appropriate design patterns as necessary. I suggest you to use "Composite" pattern to unify the interface for operator and operand.
  * When you revise your expression tree implementation in milestone2, it should not require major revision of your expression tree.  You should be able to add support for cell and cell group references with minor changes. In other worlds, your implementation should be "open for extension but closed for modification".
  * You should write JUnit tests, testing your implementation of `evaluate()` method. See the milestone description for the complete list of tests you should write.

*  In `ExpressionParser` class, complete the `postfixToExpression()` method to convert the user entered formulas to `Expression`s.
   * As explained above, in milestone 1 we will initially work on formula inputs that involve arithmetic operators and constants only. In milestone2 , you will add support for aggregate operators and cell/cell group  references.
   * A detailed pseudocode for the parser algorithm is provided in the `ExpressionParser.java` file.
   * You should write JUnit tests, testing your `postfixToExpression()` method.
   * See the milestone description for the complete list of tests you should write.

Some recommendations for milestone1:
1.  I suggest you adopt factory pattern for creating your operators. 
   * When you implement the factory pattern solution, a simple solution may involve use of "switch-case" or "if-else" statements. Even though that is a simple idea, switch-case in itself is inherently coupling. Switch-case or if-else chain works by iterating a hard coded block of logic. Even though operators are logically independent mathematically, the switch destroys that separation by sharing every operator logic together in one place (or even worse - one method, which violates the Open/Closed Principle).  So, avoid using switch or if statements. 
   * Instead, I suggest you use a HashMap with `Supplier` functional interface. HashMap is better than switch-case because of its quicker lookup time, scalability, and logic separation. You can add new operators dynamically instead of hardcoding it, making your code easier to maintain.
    This is known as the Registry Pattern in Java (Learn more about [Registry Pattern](https://java-design-patterns.com/patterns/registry/#related-patterns))

3. Try to decouple your operator implementation from the parser implementation as much as possible. The parser should only be responsible for parsing the user input and creating the expression tree. It doesn't need to know how operators are created and other details. 

4. When you define your `Operators`, I recommend moving each operator logic to its own class. It is simpler to have a line of code returning just the logic of an operator, but moving it to its class can significantly improve the operator design. Not only it will open up more options to you in refactoring, it will also encourage scalability and better encapsulation (as you are moving the logic of each operator to be independence of the factory).

5. Try to use functional programming practices such as streams and lambdas when implementing your operators. This will significantly simplify your code and improve readability.

6. Keep your application modular. Separate different functionalities into different classes and packages as necessary. This will improve code organization and maintainability.
### Milestone2:
* The given starter code defines the `Cell` class representing a cell in the grid. Since the expression operands may be either individual cells (e.g. B1) or cell groups (e.g., A1:B2), there is a need to create a uniform interface for cells and cell groups.
  * Use "Composite" pattern to create a unified interface for cells and cell groups (e.g. `CellComponent`).
    * Update the `CellRepository` and `SpreadsheetController`  classes to use this common interface instead of the `Cell` class.
    * In milestone 2, you will link the `Expression` with the cell interface to support cell references in expressions.
* Revise your  `postfixToExpression()` method in `ExpressionParser` to support cell and cell group references in formulas.
* Extend your expression tree implementation. Try to add the following requirements by extending your implementation through subclassing and composition (or aggregation) rather than modification.
  * Add support for aggregate operators (SUM, COUNT, COUNTA, and AVE)
  * Add support for cell and cell group references in expressions.
  * You should write JUnit tests, testing your implementation of `evaluate()` method. The formulas you evaluate should involve cell and group cell references.
  * See the milestone description for the complete list of tests you should write.


### Milestone3:
In milestone3, you will make sure that everything works together and have a working spreadsheet application. In addition, you will work on the the following tasks:
* Add an observer for cell and cell group. When the value of a cell is updated, the observer should notify all other cells whose expressions refer to that cell. Of course, you will use te Observer pattern to implement this.
* Extend your parser and expression implementation and add support for  additional aggregate operators.
* In milestone3, you will be given additional tasks that you will complete using appropriate design patterns. You will be able to use Generative AI tools to complete some of those. 
* See the milestone description for the complete list of tests you should write.