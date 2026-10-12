package spreadsheet.Controller;
import java.util.*;

import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.CellCoord;
import spreadsheet.Observer.Observer;


public class CellJsonFormatter {

    public static void Response(CellComponent cmp, String coord, Map<String, Object> responseGrid, SpreadsheetController controller, Set<CellComponent> visited) {

        if (cmp == null || !visited.add(cmp)) {
            return;
        }

        if (coord != null){
            responseGrid.put(coord, controller.buildCellJson(cmp));
        }

        for (Observer obs : cmp.getObservers()) {
            if (obs instanceof CellComponent) {
                CellComponent obsComp = (CellComponent) obs;

                CellCoord cc = CellRepository.getInstance().findCellCoord(obsComp);

                if (cc != null) {
                    String name = controller.convertCoord(cc.getRow(), cc.getCol());

                    if (!responseGrid.containsKey(name)) {
                        Response(obsComp, name, responseGrid, controller, visited);
                    }
                } else{
                    Response(obsComp, null, responseGrid, controller, visited);
                }
            }
        }
    }
}