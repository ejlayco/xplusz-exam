package com.xplusz.exam;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchSettings implements Serializable {

    private static final long serialVersionUID = -8113700508376458594L;

    private int pageSize;

    private int returnCount;
}
