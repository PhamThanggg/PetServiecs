package com.project.petService.services.securityService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class SecurityService {
//    CommentRepository commentRepository;
//    TicketRepository ticketRepository;
//
//    public boolean isCommentOwner(Long commentId, Authentication authentication) {
//        Comment comment = commentRepository.findById(commentId).orElse(null);
//        if (comment == null) {
//            return false;
//        }
//
//        String currentUsername = authentication.getName();
//        return comment.getUser().getEmail().equals(currentUsername);
//    }
//
//    public boolean isTicketOwner(Long ticketId, Authentication authentication) {
//        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
//        if (ticket == null) {
//            return false;
//        }
//
//        String currentUsername = authentication.getName();
//        return ticket.getInvoice().getUser().getEmail().equals(currentUsername);
//    }

}
