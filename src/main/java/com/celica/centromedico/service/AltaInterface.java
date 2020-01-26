package com.celica.centromedico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.celica.centromedico.model.Alta;


public interface AltaInterface {
	List<Alta> findAll();
	Page<Alta> findAll(Pageable pageable);
	void save(Alta a);
}
