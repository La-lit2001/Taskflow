package com.lalit.taskflow.controller;

import com.lalit.taskflow.model.*;
import com.lalit.taskflow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired private TaskRepository taskRepo;
    @Autowired private UserRepository userRepo;

    private com.lalit.taskflow.model.User getUser(UserDetails ud) {
        return userRepo.findByUsername(ud.getUsername()).orElseThrow();
    }

    // GET /api/tasks  (optional ?status=TODO or ?priority=HIGH)
    @GetMapping
    public List<Task> getAll(@AuthenticationPrincipal UserDetails ud,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String priority) {
        Long uid = getUser(ud).getId();
        if (status   != null) return taskRepo.findByUserIdAndStatus(uid, status.toUpperCase());
        if (priority != null) return taskRepo.findByUserIdAndPriority(uid, priority.toUpperCase());
        return taskRepo.findByUserId(uid);
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails ud) {
        return taskRepo.findById(id)
            .filter(t -> t.getUser().getUsername().equals(ud.getUsername()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/tasks
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task,
                                       @AuthenticationPrincipal UserDetails ud) {
        task.setUser(getUser(ud));
        return ResponseEntity.status(201).body(taskRepo.save(task));
    }

    // PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Task updated,
                                    @AuthenticationPrincipal UserDetails ud) {
        return taskRepo.findById(id)
            .filter(t -> t.getUser().getUsername().equals(ud.getUsername()))
            .map(t -> {
                t.setTitle(updated.getTitle());
                t.setDescription(updated.getDescription());
                t.setStatus(updated.getStatus());
                t.setPriority(updated.getPriority());
                t.setAssignee(updated.getAssignee());
                return ResponseEntity.ok(taskRepo.save(t));
            }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails ud) {
        return taskRepo.findById(id)
            .filter(t -> t.getUser().getUsername().equals(ud.getUsername()))
            .map(t -> {
                taskRepo.delete(t);
                return ResponseEntity.<Void>noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    // GET /api/tasks/stats
    @GetMapping("/stats")
    public Map<String, Long> stats(@AuthenticationPrincipal UserDetails ud) {
        Long uid = getUser(ud).getId();
        return Map.of(
            "total",       (long) taskRepo.findByUserId(uid).size(),
            "todo",        (long) taskRepo.findByUserIdAndStatus(uid, "TODO").size(),
            "inProgress",  (long) taskRepo.findByUserIdAndStatus(uid, "IN_PROGRESS").size(),
            "done",        (long) taskRepo.findByUserIdAndStatus(uid, "DONE").size()
        );
    }
}
