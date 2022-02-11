package com.company.web;

import com.company.entity.Transaction;
import com.company.service.StudentService;
import com.company.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactionA")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity save(@RequestBody Transaction transaction){
        if (!studentService.isExists(transaction.getUserId())){
            return ResponseEntity.ok("Bu Id dagi student topilmadi");
        }
        return ResponseEntity.ok(transactionService.save(transaction));
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @PutMapping("/put")
    public ResponseEntity update(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.update(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        transactionService.delete(id);
        return ResponseEntity.ok("ochirildi");
    }
}
