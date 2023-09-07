package com.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.entity.Stock;
import com.stock.service.StockService;


@RestController
@RequestMapping("/api/stocks")
public class StockController {

	@Autowired
	private StockService stockService;
	
	@GetMapping
	public ResponseEntity<List<Stock>> listAllStocks(){
		List<Stock> stocks = stockService.getAll();
		if(stocks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(stocks);
	}
}
