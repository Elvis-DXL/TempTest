package database.dict;

import basejpa.util.DSUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MysqlDict {

    private Connection conn;
    private String tableSchema;

    private MysqlDict() {
    }

    public static MysqlDict newInstance(Config config) {
        MysqlDict obj = new MysqlDict();
        obj.tableSchema = config.getTableSchema();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            obj.conn = DriverManager
                    .getConnection("jdbc:mysql://" + config.getHost() + ":" + config.getPort()
                                   + "/" + config.getTableSchema(),
                            config.getUser(), config.getPwd());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public void writeOut(String outFileDirUrl) {
        List<Table> tables = this.getTables();
        List<Column> columns = this.getColumns();
        Map<String, Object> paramMap = this.obtainParamMap(tables, columns);
        this.dealInfoOutFile(outFileDirUrl, paramMap);
    }

    private void dealInfoOutFile(String outFileUrl, Map<String, Object> paramMap) {
        try {
            String templateName = "template.docx";
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder().bind("columnList", policy).build();
            XWPFTemplate template = XWPFTemplate
                    .compile(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(templateName)),
                            config);
            template.render(paramMap);
            FileOutputStream fos = new FileOutputStream(outFileUrl.concat("/").concat(this.tableSchema)
                    .concat("数据字典.docx"));
            template.write(fos);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String, Object> obtainParamMap(List<Table> tables, List<Column> columns) {
        Map<String, List<Column>> listMap = columns.stream().collect(Collectors.groupingBy(Column::getTable));
        Map<String, Object> result = new HashMap<>();
        result.put("title", this.tableSchema + "数据字典");
        List<Map<String, Object>> tableList = new ArrayList<>();
        result.put("tableList", tableList);
        for (Table table : tables) {
            Map<String, Object> tableMap = new HashMap<>();
            tableList.add(tableMap);
            tableMap.put("tableName", table.getName());
            tableMap.put("condition", null != table.getComment() && table.getComment().length() != 0);
            tableMap.put("tableComment", table.getComment());
            List<Map<String, Object>> columnList = new ArrayList<>();
            tableMap.put("columnList", columnList);
            List<Column> list = listMap.get(table.getName());
            list.sort(Comparator.comparing(Column::getSort));
            for (Column column : list) {
                Map<String, Object> columnMap = new HashMap<>();
                columnList.add(columnMap);
                columnMap.put("columnName", column.getName());
                columnMap.put("columnComment", column.getComment());
                columnMap.put("columnType", column.getType());
                columnMap.put("primaryKey", "PRI".equals(column.getPrimaryKey()) ? "Y" : "");
                columnMap.put("nullable", "YES".equals(column.getNullable()) ? "N" : "Y");
            }
        }
        return result;
    }

    private List<Column> getColumns() {
        String sql = "SELECT ORDINAL_POSITION,TABLE_NAME,COLUMN_NAME,COLUMN_COMMENT,COLUMN_TYPE," +
                     "COLUMN_KEY,IS_NULLABLE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Column> columns = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.tableSchema);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                columns.add(new Column()
                        .setTable(rs.getString("TABLE_NAME"))
                        .setName(rs.getString("COLUMN_NAME"))
                        .setNullable(rs.getString("IS_NULLABLE"))
                        .setPrimaryKey(rs.getString("COLUMN_KEY"))
                        .setType(rs.getString("COLUMN_TYPE"))
                        .setSort(rs.getInt("ORDINAL_POSITION"))
                        .setComment(rs.getString("COLUMN_COMMENT")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DSUtil.IOTool.closeStream(pstmt, rs);
        }
        return columns;
    }

    private List<Table> getTables() {
        String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Table> tables = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.tableSchema);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tables.add(new Table()
                        .setName(rs.getString("TABLE_NAME")).setComment(rs.getString("TABLE_COMMENT")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DSUtil.IOTool.closeStream(pstmt, rs);
        }
        return tables;
    }
}