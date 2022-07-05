/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dal.ClassUserDao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import model.ClassUser;
import model.UserImport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author pallgree
 */
public class xlsxUtils {

    public static ArrayList<UserImport> readFile(String path) {
        ArrayList<UserImport> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<org.apache.poi.ss.usermodel.Row> itr = sheet.iterator();
            while (itr.hasNext()) {

                org.apache.poi.ss.usermodel.Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 

                while (cellIterator.hasNext()) {
                    UserImport user = new UserImport();
                    Cell cell = cellIterator.next();
                    user.setTeam(cell.toString());
                    cell = cellIterator.next();
                    user.setFull_name(cell.toString());
                    cell = cellIterator.next();
                    user.setRoll_number(cell.toString());
                    cell = cellIterator.next();
                    user.setEmail(cell.toString());
                    try {
                        cell = cellIterator.next();
                        if (cell.toString().equals("true") || cell.toString().equals("TRUE")) {
                            user.setLeader(true);
                        } else {
                            user.setLeader(false);
                        }

                    } catch (Exception e) {
                        user.setLeader(false);
                    }

                    list.add(user);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int writeFile(ArrayList<ClassUser> list) throws FileNotFoundException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Class User");
        XSSFRow row;
        Map<String, Object[]> studentData = new TreeMap<String, Object[]>();
        studentData.put(
                "1",
                new Object[]{"full_name", "roll_number", "email", "class_id", "team_id",
                     "user_id", "team_lead", "dropout-date", "user_note", "ongoing_eval",
                     "final_pres_eval", "final_topic_eval", "status"});
        int count = 1;
        for (ClassUser i : list) {
            String index = String.valueOf(++count);
            studentData.put(index, new Object[]{i.getFull_name(), i.getRoll_number(), i.getEmail(),
                 String.valueOf(i.getClass_id()), String.valueOf(i.getTeam_id()), String.valueOf(i.getUser_id()),
                 String.valueOf(i.getTeam_lead()), i.getDropout_date(), i.getUser_notes(), i.getOngoing_eval(),
                 i.getFinal_pres__eval(), i.getFinal_topic_eval(), String.valueOf(i.getStatus())});
        }
        Set<String> keyid = studentData.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = studentData.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }

        FileOutputStream out = new FileOutputStream(
                new File("/Users/pallgree/Desktop/g4/swp-project/web/assets/file-export-import/class-user.xlsx"));

        workbook.write(out);
        out.close();
        return 1;
    }

    public static void main(String[] args) throws IOException {
//        ClassUserDao daoClassU = new ClassUserDao();
//        ArrayList<ClassUser> list = daoClassU.search("", 100, 0,"SE1619", "");
//         xlsxUtils.writeFile(list);
        ArrayList<UserImport> list = xlsxUtils.readFile("/Users/pallgree/Desktop/g4/swp-project/web/assets/file-export-import/class-user.xlsx");
        for (UserImport i : list) {
            System.out.println(i.toString());
        }

    }
}
