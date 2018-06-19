package laboratorio.utilidades;

import javax.swing.table.*;
import java.util.*;

public abstract class ModeloTabela<T> extends AbstractTableModel {

    @Override
    public abstract String getColumnName(int column);

    @Override
    public int getRowCount() {
        return getList().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumn(getList().get(rowIndex), columnIndex);
    }

    public abstract Object getColumn(T object, int column);

    public T getObject(int row) {
        return getList().get(row);
    }

    public abstract List<T> getList();
}
