package com.company.web;

import com.company.entity.Transaction;
import com.company.service.StudentService;
import com.company.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactionA")
@Tag(name = "TransactionController", description = "Student tranzaksiyalari")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    StudentService studentService;

    @Operation(summary = "Student id si bo'yicha tranzaksiya qo'shish")
    @PostMapping
    public ResponseEntity save(@RequestBody Transaction transaction) {
        if (studentService.countById(transaction.getUserId()) != 1) {
            return ResponseEntity.ok("Bu Id dagi student topilmadi");
        }
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @Operation(summary = "Student tranzaksiya listini olish")
    @GetMapping("/all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @Operation(summary = "Student id si bo'yicha tranzaksiyani o'zgartirish")
    @PutMapping("/put")
    public ResponseEntity update(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.update(transaction));
    }

    @Operation(summary = "Student id si bo'yicha tranzaksiyani olish")
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getOne(id));
    }

    @Operation(summary = "Student id si bo'yicha tranzaksiya o'chirish")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.ok("ochirildi");
    }
}
