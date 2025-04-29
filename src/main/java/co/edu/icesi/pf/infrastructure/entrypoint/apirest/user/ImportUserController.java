package co.edu.icesi.pf.infrastructure.entrypoint.apirest.user;

import co.edu.icesi.pf.domain.usecase.user.ImportUsersUseCase;
import co.edu.icesi.pf.infrastructure.drivenadapter.excel.ExcelUsersReaderAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users/import")
@RequiredArgsConstructor
public class ImportUserController {

    private final ImportUsersUseCase importUsersUseCase;
    private final ExcelUsersReaderAdapter excelUserReaderAdapter;

    @PostMapping
    public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            var users = excelUserReaderAdapter.readUsersFromExcel(file.getInputStream());
            importUsersUseCase.importUsers(users);
            return ResponseEntity.ok("Users imported successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing the file: " + e.getMessage());
        }
    }
}
