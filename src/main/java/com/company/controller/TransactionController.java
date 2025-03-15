package com.company.controller;

import com.company.domain.transactions.TransactionStatus;
import com.company.dto.PagingDto;
import com.company.dto.ResponseDto;
import com.company.dto.TransactionDto;
import com.company.form.TransactionParamsForm;
import com.company.form.transactions.TransactionForm;
import com.company.service.TransactionService;
import com.company.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sukhrob
 */
@Slf4j
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/paging")
    public ResponseDto<PagingDto<TransactionDto>> paging(@Valid @RequestBody TransactionParamsForm form) {
        form.setProfileId(CurrentUserUtil.currentUser().getId());

        return new ResponseDto<>(transactionService.getByParams(form));
    }

    @GetMapping("/debit/{transactionId}")
    public ResponseDto<TransactionDto> getDebitTransaction(@PathVariable String transactionId) {

        TransactionDto dto = transactionService.getTransactionBySenderProfile(transactionId, CurrentUserUtil.currentUser().getId());
        return new ResponseDto<>(dto);
    }

    @GetMapping("/credit/{transactionId}")
    public ResponseDto<TransactionDto> getCreditTransaction(@PathVariable String transactionId) {

        TransactionDto dto = transactionService.getTransactionByRecipientProfile(transactionId, CurrentUserUtil.currentUser().getId());
        return new ResponseDto<>(dto);
    }

    @PostMapping("/create/{status}")
    public ResponseDto<?> createTransaction(@PathVariable("status") String transactionStatus,
                                            @Valid @RequestBody TransactionForm form) {
        log.info("Rest request to create successful transaction: {}, status: {}", form, transactionStatus);

        // Set current profile id
        form.setProfileId(CurrentUserUtil.currentUser().getId());
        return new ResponseDto<>(transactionService.doTransaction(form, TransactionStatus.valueOf(transactionStatus)));
    }
}
