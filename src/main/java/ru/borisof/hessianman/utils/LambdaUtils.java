package ru.borisof.hessianman.utils;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

@Slf4j
public abstract class LambdaUtils {

  public static File resourceToFileConversion(Resource res) {
    try {
      return res.getFile();
    } catch (IOException e) {
      log.warn("Can't load resource with name {}", res.getFilename());
    }
    return null;
  }

}
