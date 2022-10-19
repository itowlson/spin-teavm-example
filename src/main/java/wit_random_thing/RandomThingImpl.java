package wit_random_thing;

public class RandomThingImpl {
  public static RandomThing.Result<String, RandomThing.Error> getRandomThing(RandomThing.Request request) {
    if (request.tag == RandomThing.Request.JOKE) {
      return RandomThing.Result.ok("Look here this is a very serious workplace and we have no time for jokes");
    } else {
      return RandomThing.Result.ok("No animals except service animals are permitted on enterprise facilities");
    }
  }
}
