import java.util.*;

class SQL {

    class Table {
        int cols;
        int nextId;
        Map<Integer, List<String>> rows;

        Table(int cols) {
            this.cols = cols;
            this.nextId = 1;
            this.rows = new LinkedHashMap<>();
        }
    }

    private Map<String, Table> tables;

    public SQL(List<String> names, List<Integer> columns) {
        tables = new HashMap<>();

        for (int i = 0; i < names.size(); i++) {
            tables.put(names.get(i), new Table(columns.get(i)));
        }
    }

    public boolean ins(String name, List<String> row) {
        if (!tables.containsKey(name)) return false;

        Table table = tables.get(name);

        if (row.size() != table.cols) return false;

        table.rows.put(table.nextId++, new ArrayList<>(row));
        return true;
    }

    public void rmv(String name, int rowId) {
        if (!tables.containsKey(name)) return;

        tables.get(name).rows.remove(rowId);
    }

    public String sel(String name, int rowId, int columnId) {
        if (!tables.containsKey(name)) return "<null>";

        Table table = tables.get(name);

        if (!table.rows.containsKey(rowId)) return "<null>";

        if (columnId < 1 || columnId > table.cols) return "<null>";

        return table.rows.get(rowId).get(columnId - 1);
    }

    public List<String> exp(String name) {
        List<String> ans = new ArrayList<>();

        if (!tables.containsKey(name)) return ans;

        Table table = tables.get(name);

        for (Map.Entry<Integer, List<String>> entry : table.rows.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey());

            for (String s : entry.getValue()) {
                sb.append(",").append(s);
            }

            ans.add(sb.toString());
        }

        return ans;
    }
}