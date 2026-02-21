# Design Decisions
Milestone 2
Christine Ngo

> Used the Composite pattern for cells and cell groups to enable uniform reference of individual cells and groups of cells. This allows for easier use and manipulation of both types, since they can be treated the same way in the code.
> 
> Decided not to use lambdas for the Count and CountA functions since using streams would've been more complex and checking the instance of the operands directly makes it more straightforward.
> 
> Added getNumCells() and getNumNonEmptyCells() methods to the CellGroup class to provide a way to easily retrieve the number of cells and non-empty cells in a group, which is useful for implementing the Count and CountA functions.
> 
> Extended the OperatorExpression class for CellReferenceExpressions with a reference to a CellComponent to enable cell references in expressions.
> 
> Used instanceOf for expressionToString() to check the type of the expression, which allows for proper formatting for different types of expressions in the output.
> 
> Added getOperatorString() to OperatorFactory to provide a way to get the string representation of an operator, which is useful for expressionToString() when formatting operator expressions. It uses the same HashMap for a fast lookup and to keep it organized and consistent. Also moved creating the HashMap outside getOperator() so it doesn't have to be recreated every time an operator is requested.