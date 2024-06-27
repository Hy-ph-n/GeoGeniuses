package geogeniuses;

import java.sql.*;
import java.util.Calendar;
import java.util.Locale;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.filechooser.FileSystemView;

class Purchase {

    ResultSet rs;
    ResultSetMetaData md;
    String baseIntro = "<!doctype html><html><body>";
    String title = "";
    String baseBody = "";
    String baseClose = "</table></body></html>";
    String header = "";
    String style = "";
    static String time;
 
   
    Purchase(String title, ResultSet rs) throws Exception {
        this.title = title;
        this.rs = rs;
        md = rs.getMetaData();
        setStyle();
        setHeader();
        fillTable();
        buildPurchase();
    }

    void setStyle() {

        style = "\n<style> table{"
                + "border-collapse: collapse;"
                + "border-block-style: groove;"
                + "margin-left: auto;"
                + "margin-right: auto;}"
                + "th{  background-color: lightsteelblue;"
                + "padding: 10px;}"
                + "td { padding: 6px;}"
                + "tr:nth-child(odd){background-color: #ffa69e;}"
                + "tr:nth-child(even){background-color: #faf3dd;}"
                + ".TableTitle{"
                + "text-align: center;"
                + "font-weight: bold;"
                + "font-size: 20px;"
                + "}"
                + "</style>\n";
    }

    void setHeader() throws Exception {
        header = "<div class = TableTitle>" + title + "</div><table><tr>\n";
        for (int i = 0; i < md.getColumnCount(); i++) {
            header += "<th>" + md.getColumnName(i + 1).toUpperCase() + "</th>";
        }
        header += "</tr>\n";
    }

    // Adds rows of table data
    void fillTable() throws Exception {
        while (rs.next()) {
            baseBody += "<tr>";
            for (int i = 1; i < md.getColumnCount() + 1; i++) {
                baseBody += "<td>" + rs.getString(i) + "</td>";
            }
            baseBody += "</tr>\n";
        }
    }

    void buildPurchase() throws Exception {
        time = Calendar.getInstance(Locale.getDefault()).getTime().toString();
        time = time.substring(3, 19);
        time = time.replace(" ", "");
        time = time.replace(":", "_");
        System.out.println("Time: " + time);
        String fileName = time + "report.html";
        String filePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        filePath = filePath + "\\Reports\\" + fileName;
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        String finalReport = baseIntro + style + header + baseBody + baseClose;
        bw.write(finalReport);
        bw.close();
        System.out.println("Report saved ");

    }
}