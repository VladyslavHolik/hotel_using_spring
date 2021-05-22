package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.service.RoomService;
import holik.hotel.spring.web.sort.SortMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String listRooms(Model model, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, HttpSession session) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Sort sortMethod = getSortMethod(session);

        Page<Room> roomPage = roomService.findPaginated(PageRequest.of(currentPage - 1, pageSize, sortMethod));
        model.addAttribute("roomPage", roomPage);

        int totalPages = roomPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "rooms";
    }

    @PostMapping("/rooms/setSort")
    public String setSorting(@RequestParam("sortMethod") Optional<String> sortMethod, HttpSession session) {
        if (sortMethod.isPresent()) {
            String sortName = sortMethod.get();
            session.setAttribute("sortMethod", SortMethod.getMethod(sortName).getSort());
        }
        return "redirect:/rooms";
    }

    @GetMapping("/room")
    public String roomPage(Model model, @RequestParam("id") Integer id) {
        Optional<Room> optionalRoom = roomService.getRoomById(id);
        if (optionalRoom.isPresent()) {
            model.addAttribute("room", optionalRoom.get());
            return "room";
        }
        return "404";
    }

    private Sort getSortMethod(HttpSession session) {
        Sort sortMethod = (Sort) session.getAttribute("sortMethod");
        if (sortMethod == null) {
            sortMethod = Sort.by("price");
        }
        return sortMethod;
    }
}
