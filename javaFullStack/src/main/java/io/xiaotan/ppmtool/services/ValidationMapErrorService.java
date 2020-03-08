package io.xiaotan.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationMapErrorService {

    public ResponseEntity<?> ValidationMapService(BindingResult result){
        //clear the error messages
        if(result.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            //result.getFieldErrors() return type is List<FieldError>
            //Loop through List<FieldError>
            for (FieldError error: result.getFieldErrors()){
                //.put(key,value),add to Map
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
