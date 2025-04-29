package co.edu.icesi.pf.infrastructure.drivenadapter.excel;

import co.edu.icesi.pf.domain.model.records.UserImportRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelUsersReaderAdapter {

    public List<UserImportRequest> readUsersFromExcel(InputStream inputStream) throws Exception {
        List<UserImportRequest> users = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            UserImportRequest user = new UserImportRequest(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue(),
                    (int) row.getCell(3).getNumericCellValue(),
                    row.getCell(4).getBooleanCellValue(),
                    row.getCell(5).getStringCellValue(),
                    row.getCell(6).getStringCellValue(),
                    row.getCell(7).getStringCellValue(),
                    row.getCell(8).getStringCellValue(),
                    row.getCell(9).getStringCellValue(),
                    row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null,
                    row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null
            );

            users.add(user);
        }

        workbook.close();
        return users;
    }
}
