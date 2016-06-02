package villagerrank.ranking;

import villagerrank.util.HTMLScrape;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import villagerrank.util.JSONUtil;

/**
 *
 * @author owner
 */
public class ACNLWorker {

    public static Map<String, Villager> villagerMap = new HashMap<>();
    
    public ACNLWorker() {
        
    }

    public void createMapAndImages(boolean saveImages) {
        JSONParser parser = new JSONParser();

        try (FileReader fr = new FileReader("villagers.json")) {
            JSONObject jObject = (JSONObject) parser.parse(fr);

            for (Object villager : jObject.keySet()) {
                JSONObject villagerData = JSONUtil.getJSONObject(jObject, (String) villager);

                Villager vil = new Villager();
                String villagerName = JSONUtil.getJSONObjectString(villagerData, "name_en");

                vil.setName(villagerName);
                vil.setType(JSONUtil.getJSONObjectString(villagerData, "type_en"));
                vil.setSpecies(JSONUtil.getJSONObjectString(villagerData, "species_en"));
                vil.setGender(JSONUtil.getJSONObjectString(villagerData, "gender"));
                vil.setTier((int) JSONUtil.getJSONObjectLong(villagerData, "amv_tier"));

                villagerMap.put(villagerName, vil);

                if (!saveImages) {
                    continue;
                }

                // We need to replace spaces with _
                String scrapeName = villagerName;
                scrapeName = scrapeName.replace("\\s", "_");

                // I was thinking of automatically trying the new URL, but
                // there are just way too many special cases, so I found each
                // case that was not working and included it here
                switch (scrapeName) {
                    case "Aurora":
                    case "Cherry":
                        scrapeName = scrapeName + "_(villager)";
                        break;
                    case "Carmen":
                        scrapeName = "Carmen_(rabbit)";
                        break;
                    case "Hazel":
                        scrapeName = "Hazel_(New_Leaf)";
                        break;
                    case "Sally": // Don't even process sally, renamed to Hazel
                        villagerMap.remove("Sally");
                        continue;
                }

                String url = HTMLScrape.scrapeImage("http://animalcrossing.wikia.com/wiki/" + scrapeName, scrapeName);

                if (url == null) {
                    System.out.println("Could not scrape " + villagerName);
                    continue;
                }

                HTMLScrape.saveImage(url, villagerName + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ACNLSorter rankVillager() {
        Villager[] villagers = new Villager[villagerMap.size()];
        ACNLWorker.villagerMap.values().toArray(villagers);
        
        ACNLSorter as = new ACNLSorter(villagers);
        as.initiateList1();
        return as;
    }

//    public static void main(String[] args) {
//        createMapAndImages(false);
//        rankVillager();
//    }
}
