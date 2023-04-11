package com.lcwd.electroinic.store.dtos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type)
    {
        List<U> content = page.getContent();
        List<V> collect = content.stream().map((obj) -> {
            return new ModelMapper().map(obj, type);
        }).collect(Collectors.toList());
        PageableResponse<V> response=new PageableResponse<>();
        response.setContent(collect);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}
