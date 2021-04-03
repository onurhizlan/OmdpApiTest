package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {

  private String title;
  private String year;
  private String imdbID;
  private String type;
  private String poster;
}
