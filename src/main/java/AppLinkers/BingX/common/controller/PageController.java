package AppLinkers.BingX.common.controller;

import AppLinkers.BingX.admin.service.AdvertisementService;
import AppLinkers.BingX.admin.service.AnnounceService;
import AppLinkers.BingX.admin.service.EventService;
import AppLinkers.BingX.admin.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AnnounceService announceService;
    private final GuideService guideService;
    private final AdvertisementService advertisementService;
    private final EventService eventService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("announceList", announceService.readAnnounceMainList());
        model.addAttribute("guideList", guideService.readGuideMainList());
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        model.addAttribute("eventList", eventService.readEventMainList());
        return "common/main";
    }

    @GetMapping("/announce")
    public String announcePage(Model model, Pageable pageable){
        model.addAttribute("announceList", announceService.readAnnounceList(pageable));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/announce";
    }

    @GetMapping("/announce/{id}")
    public String announceDetailPage(@PathVariable Long id, Model model){
        model.addAttribute("announce", announceService.readAnnounce(id));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/announce_detail";
    }

    @GetMapping("/guide")
    public String guidePage(Model model, Pageable pageable){
        model.addAttribute("guideList", guideService.readGuideList(pageable));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/guide";
    }

    @GetMapping("/guide/{id}")
    public String guideDetailPage(@PathVariable Long id, Model model){
        model.addAttribute("guide", guideService.readGuide(id));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/guide_detail";
    }

    @GetMapping("/event")
    public String eventPage(Model model, Pageable pageable){
        model.addAttribute("eventList", eventService.readEventList(pageable));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/event";
    }

    @GetMapping("/event/{id}")
    public String eventDetailPage(@PathVariable Long id, Model model){
        model.addAttribute("event", eventService.readEvent(id));
        model.addAttribute("advertisementList", advertisementService.readAdvertisementMainList());
        return "common/event_detail";
    }

    @GetMapping("/copytrading")
    public String copyTrading(){
        return "common/copytrading";
    }
}
