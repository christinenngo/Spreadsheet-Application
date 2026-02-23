# Design Decisions
Milestone 2
Christine Ngo

> Used the Composite pattern for cells and cell groups to enable uniform reference of individual cells and groups of cells. This allows for easier use and manipulation of both types, since they can be treated the same way in the code.
> 
> Decided not to use lambdas for the Count and CountA functions since using streams would've been more complex and checking the instance of the operands directly makes it more straightforward.
> 
> Added getNumCells() and getNumNonEmptyCells() methods to the CellGroup class to provide a way to easily retrieve the number of cells and non-empty cells in a group, which is useful for implementing the Count and CountA functions.
> 
> Used instanceOf for expressionToString() to check the type of the expression, which allows for properly formatting the different types of expressions in the output and keeping the original expression interface.
> 
> Added getOperatorString() to OperatorFactory to provide a way to get the string representation of an operator, which is useful for expressionToString() when formatting operator expressions. It uses the same HashMap for a fast lookup and to keep it organized and consistent. Also moved creating the HashMap outside getOperator() so it doesn't have to be recreated every time an operator is requested.
> 
> Only needed to change any references from Cell to CellComponent to support cell references, such as in CellRepository.
> 
> Extended the OperatorExpression class for CellReferenceExpressions with a reference to a CellComponent to keep track of cell references and cell groups in expressions. This keeps the structure of the expression tree consistent.
> 
> Also extended OperatorExpression for aggregate operators like Sum and Count to allow for expressions that operate on multiple cells, which is necessary for cell group references.