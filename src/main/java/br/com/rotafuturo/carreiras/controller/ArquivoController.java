package br.com.rotafuturo.carreiras.controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.rotafuturo.carreiras.storage.FileCryptoUtil;
import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api/arquivo")
public class ArquivoController {
	private final String STORAGE_ROOT = "storage";
	@Autowired
	private br.com.rotafuturo.carreiras.service.ArquivoService arquivoService;
	@PostMapping("/upload/{usuId}")
	public ResponseEntity<?> uploadArquivo(@PathVariable Integer usuId, @RequestParam("file") MultipartFile file) {
		try {
			Path userDir = Paths.get(STORAGE_ROOT, String.valueOf(usuId));
			if (!Files.exists(userDir)) {
				Files.createDirectories(userDir);
			}
			String originalFileName = file.getOriginalFilename();
			String sanitizedFileName = java.util.UUID.randomUUID().toString() + "_"
					+ (originalFileName != null ? originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_") : "file");
			Path filePath = userDir.resolve(sanitizedFileName);
			FileCryptoUtil.encryptAndSave(filePath, file.getBytes());
			br.com.rotafuturo.carreiras.model.ArquivoBean arquivo = new br.com.rotafuturo.carreiras.model.ArquivoBean();
			arquivo.setArqDatacadastro(java.time.LocalDate.now());
			arquivo.setArqHoracadastro(java.time.LocalTime.now());
			arquivo.setArqTamanho((int) file.getSize());
			arquivo.setArqDescricao(sanitizedFileName);
			arquivo.setArqExtensao(sanitizedFileName.contains(".")
					? sanitizedFileName.substring(sanitizedFileName.lastIndexOf('.') + 1)
					: "");
			arquivo.setArqEndereco(filePath.toString());
			br.com.rotafuturo.carreiras.model.UsuarioBean usuario = new br.com.rotafuturo.carreiras.model.UsuarioBean();
			usuario.setUsuId(usuId);
			arquivo.setUsuario(usuario);
			arquivoService.salvar(arquivo);
			String url = "/api/arquivo/view/" + usuId + "/" + sanitizedFileName;
			return ResponseEntity.ok(java.util.Collections.singletonMap("url", url));
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao enviar arquivo: " + e.getMessage());
		}
	}
	@GetMapping("/download/{usuId}/{fileName}")
	public ResponseEntity<?> downloadArquivo(@PathVariable Integer usuId, @PathVariable String fileName,
			HttpServletRequest request) {
		try {
			Path filePath = Paths.get(STORAGE_ROOT, String.valueOf(usuId), fileName);
			if (!Files.exists(filePath)) {
				return ResponseEntity.notFound().build();
			}
			byte[] decrypted = FileCryptoUtil.loadAndDecrypt(filePath);
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + fileName)
					.body(decrypted);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao baixar arquivo: " + e.getMessage());
		}
	}
	@GetMapping("/view/{usuId}/{fileName}")
	public ResponseEntity<?> viewArquivo(@PathVariable Integer usuId, @PathVariable String fileName,
			HttpServletRequest request) {
		try {
			Path filePath = Paths.get(STORAGE_ROOT, String.valueOf(usuId), fileName);
			if (!Files.exists(filePath)) {
				return ResponseEntity.notFound().build();
			}
			byte[] decrypted = FileCryptoUtil.loadAndDecrypt(filePath);
			String contentType = null;
			String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase() : "";
			switch (ext) {
			case "jpg":
			case "jpeg":
				contentType = "image/jpeg";
				break;
			case "png":
				contentType = "image/png";
				break;
			case "gif":
				contentType = "image/gif";
				break;
			case "bmp":
				contentType = "image/bmp";
				break;
			case "webp":
				contentType = "image/webp";
				break;
			default:
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().header("Content-Type", contentType).body(decrypted);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao visualizar arquivo: " + e.getMessage());
		}
	}
}
