package com.company.service;

import com.company.entity.Transaction;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {

    String url = "http://localhost:8081/transaction";


    public Transaction save( Transaction transaction){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction> transactionResponseEntity = restTemplate.postForEntity(url, transaction, Transaction.class);
        return transactionResponseEntity.getBody();
    }

    public List<Transaction> findAll(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List>http=new HttpEntity<>(httpHeaders);
        return (List<Transaction>) restTemplate.exchange(url, HttpMethod.GET,http,List.class).getBody();
    }

    public Transaction update(Transaction transaction){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Transaction> http=new HttpEntity<>(transaction,httpHeaders);
        return restTemplate.exchange(url+"/put",HttpMethod.PUT,http,Transaction.class).getBody();
    }

    public Transaction getOne(Long id){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Transaction>http=new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url+"/"+id, HttpMethod.GET,http,Transaction.class).getBody();
    }

    public void delete(Long id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url+"/"+id,Transaction.class);
    }


}
