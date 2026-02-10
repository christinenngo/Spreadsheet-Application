# Design Decisions
Christine Ngo

> Used the Composite design pattern to allow for the tree structure of expressions, so that each expression can be evaluated recursively with a leaf (OperandExpression) and node (OperatorExpression). This allows for flexibility in the types of expressions that can be created and evaluated.
> 
> Used the factory pattern to create instances of the different types of operators to encapsulate the creation logic and make it easier to add new types of operators in the future. It also has the client code only interacting with the factory to create operators, rather than directly instantiating them. I also made it static to avoid the need for creating an instance of the factory every time.
> 
> Stored operators in a HashMap in the OperatorFactory to allow for easy retrieval and management of operators, as well as to avoid hardcoding operators in the client code. This allows for dynamic addition or removal of operators without drastically changing the code.
> 
> Put each operator's logic in its own class that extends OperatorExpression to follow the Single Responsibility Principle, making it easier to maintain and update each operator's logic independently. This also allows for better organization and easy additions to the type of operators available.
> 
> Used streams in the evaluate() methods of the operators for more concise code and more efficiency when performing operations on many operands.
> 
> Implemented OperatorExpression as an abstract class to allow for different types of operator expressions that share common functionality without having to reimplement the same methods for each, which allows for better code reuse and easier maintenance.
> 
> Made the DivideOperator throw an ArithmeticException when dividing by zero to handle the edge case and prevent undefined results.
> 
> I used TDD development by writing tests for the parser and evaluator before implementing the logic for them, which helped ensure that the code was correct and met the requirements. This also made it easier to debug and refactor as I had tests to catch any issues during development.