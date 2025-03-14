package com.company.controller;

import com.company.dto.ResponseDto;
import com.company.form.TransactionForm;
import com.company.service.TransactionService;
import com.company.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create/success")
    public ResponseDto<?> createSuccessfulTransaction(@Valid @RequestBody TransactionForm form) {
        log.info("Rest request to create successful transaction {}", form);

        // Set current profile id
        form.setProfileId(CurrentUserUtil.currentUser().getId());

        return new ResponseDto<>(transactionService.doSuccessfulTransaction(form));
    }
}
