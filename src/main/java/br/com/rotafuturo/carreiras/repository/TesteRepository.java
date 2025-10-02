package br.com.rotafuturo.carreiras.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.rotafuturo.carreiras.model.TesteBean;
public interface TesteRepository extends JpaRepository<TesteBean, Integer> {
    @Query("SELECT t FROM TesteBean t WHERE t.area.areaId = :areaId")
    List<TesteBean> findByAreaId(@Param("areaId") Integer areaId);
    @Query("SELECT t FROM TesteBean t WHERE t.areaSub.areasId = :areaSubId")
    List<TesteBean> findByAreaSubId(@Param("areaSubId") Integer areaSubId);
    @Query("SELECT t FROM TesteBean t WHERE t.area IS NOT NULL")
    List<TesteBean> findSubareaTests();
    @Query("SELECT t FROM TesteBean t WHERE t.area IS NULL")
    List<TesteBean> findVocationalTests();
}
