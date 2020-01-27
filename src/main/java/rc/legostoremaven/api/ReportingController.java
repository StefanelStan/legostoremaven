package rc.legostoremaven.api;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rc.legostoremaven.model.AvgRatingModel;
import rc.legostoremaven.persistence.ReportService;

import java.util.List;

@RestController
@RequestMapping("/legostore/api/reports")
public class ReportingController {
    private ReportService reportService;

    public ReportingController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/averageRating")
    public List<AvgRatingModel> getAvgRatingReports() {
        return this.reportService.getAvgRatingReports();
    }
}
