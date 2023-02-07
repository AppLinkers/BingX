package AppLinkers.BingX.admin.controller;


import AppLinkers.BingX.admin.dto.CreateAdvertisementReq;
import AppLinkers.BingX.admin.dto.CreateAnnounceReq;
import AppLinkers.BingX.admin.dto.CreateEventReq;
import AppLinkers.BingX.admin.dto.CreateGuideReq;
import AppLinkers.BingX.admin.service.AdvertisementService;
import AppLinkers.BingX.admin.service.AnnounceService;
import AppLinkers.BingX.admin.service.EventService;
import AppLinkers.BingX.admin.service.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AnnounceService announceService;
    private final GuideService guideService;
    private final AdvertisementService advertisementService;
    private final EventService eventService;

    ///// ANNOUNCE CONTROLLER /////////

    @GetMapping("/admin/announce")
    public String adminAnnouncePage(Model model, Pageable pageable) {
        model.addAttribute("announceList", announceService.readAnnounceList(pageable));
        return "admin/announce";
    }

    @DeleteMapping("/admin/announce/{id}")
    public String deleteAnnounce(@PathVariable Long id) {
        announceService.deleteAnnounce(id);

        return "redirect:/admin/announce";
    }

    @GetMapping("/admin/announce/form")
    public String adminAnnounceForm(CreateAnnounceReq createAnnounceReq) {
        return "admin/announce_form";
    }

    @PostMapping("/admin/announce")
    public String createAnnounce(CreateAnnounceReq createAnnounceReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        announceService.createAnnounce(createAnnounceReq, userLoginId);

        return "redirect:/admin/announce";
    }


    ///////GUIDE CONTROLLER ///////

    @GetMapping("/admin/guide")
    public String adminGuidePage(Model model, Pageable pageable) {
        model.addAttribute("guideList", guideService.readGuideList(pageable));
        return "admin/guide";
    }

    @DeleteMapping("/admin/guide/{id}")
    public String deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);

        return "redirect:/admin/guide";
    }

    @GetMapping("/admin/guide/form")
    public String adminGuideForm(CreateGuideReq createGuideReq) {
        return "admin/guide_form";
    }

    @PostMapping("/admin/guide")
    public String createGuide(CreateGuideReq createGuideReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        guideService.createGuide(createGuideReq, userLoginId);

        return "redirect:/admin/guide";
    }

    ///////ADVERTISEMENT CONTROLLER ///////
    @GetMapping("/admin/ad_banner")
    public String adminAdvertisementPage(Model model, Pageable pageable) {
        model.addAttribute("advertisementList", advertisementService.readAdvertisementList(pageable));
        return "admin/ad_banner";
    }

    @DeleteMapping("/admin/ad_banner/{id}")
    public String deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);

        return "redirect:/admin/ad_banner";
    }

    @GetMapping("/admin/ad_banner/form")
    public String adminAdvertisementForm(CreateAdvertisementReq createAdvertisementReq) {
        return "admin/ad_banner_form";
    }

    @PostMapping("/admin/ad_banner")
    public String createAdvertisement(CreateAdvertisementReq createAdvertisementReq) throws IOException {
        advertisementService.createAdvertisement(createAdvertisementReq);

        return "redirect:/admin/ad_banner";
    }

    ///////Event CONTROLLER ///////
    @GetMapping("/admin/event")
    public String adminEventPage(Model model, Pageable pageable) {
        model.addAttribute("eventList", eventService.readEventList(pageable));
        return "admin/event";
    }

    @DeleteMapping("/admin/event/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);

        return "redirect:/admin/event";
    }

    @GetMapping("/admin/event/form")
    public String adminEventForm(CreateEventReq createEventReq) {
        return "admin/event_form";
    }

    @PostMapping("/admin/event")
    public String createEvent(CreateEventReq createEventReq) throws IOException {
        String userLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        eventService.createEvent(createEventReq, userLoginId);

        return "redirect:/admin/event";
    }
}
