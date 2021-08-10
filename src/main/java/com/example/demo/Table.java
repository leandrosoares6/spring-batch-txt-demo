package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {

  private String name;

  private List<Field> fields = new ArrayList<>();

  public Table(final String line) {
    if (line.startsWith("|")) {
      List<String> fieldsFromLine = new ArrayList<>(List.of(line.split("\\|")))
        .stream()
        .filter(t -> t != null && !t.isBlank())
        .collect(Collectors.toList());

      this.name = fieldsFromLine.get(0);
      fieldsFromLine.remove(0);

      if (!fieldsFromLine.isEmpty()) {
        fieldsFromLine.forEach(fieldName -> {
          fields.add(new Field(fieldName));
        });
      }
    }
  }

  public String getName() {
    return name;
  }

  public List<Field> getFields() {
    return fields;
  }  

}
