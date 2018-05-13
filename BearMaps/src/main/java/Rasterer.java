import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    private double cor1, cor2, cor3, cor4;

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        boolean querysuccess = querySuccess(params);
        if (!querysuccess) {
            results.put("query_success", false);
            return results;
        }
        String[][] renderGrid = render(params);
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", cor1);
        results.put("raster_ul_lat", cor3);
        results.put("raster_lr_lon", cor2);
        results.put("raster_lr_lat", cor4);
        results.put("depth", depth(params));
        results.put("query_success", querysuccess);
        return results;
    }

    private int depth(Map<String, Double> params) {
        double d0 = (-122.2119140625 + 122.2998046875) / 256;
        double d1 = (-122.255859375 + 122.2998046875) / 256;
        double d2 = (-122.27783203125 + 122.2998046875) / 256;
        double d3 = (-122.288818359375 + 122.2998046875) / 256;
        double d4 = (-122.2943115234375 + 122.2998046875) / 256;
        double d5 = (-122.29705810546875 + 122.2998046875) / 256;
        double d6 = (-122.29843139648438 + 122.2998046875) / 256;
        double d7 = (-122.29911804199219 + 122.2998046875) / 256;
        double[] possibledepths = {d0, d1, d2, d3, d4, d5, d6, d7};
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double lonDPP = (lrlon - ullon) / w;
        for (int i = 0; i < 8; i++) {
            if (lonDPP >= possibledepths[i]) {
                return i;
            }
        }
        return 7;
    }

    private boolean querySuccess(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        boolean a = ROOT_ULLON < lrlon;
        boolean b = ROOT_LRLON > ullon;
        boolean c = ROOT_ULLAT > lrlat;
        boolean d = ROOT_LRLAT < ullat;
        return a && b && c && d;
    }

    private String[][] render(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        int depth = depth(params);
        int uplon = -1;
        int downlon = -1;
        int uplat = -1;
        int downlat = -1;
        double d0 = ROOT_LRLON - ROOT_ULLON;
        double[] pos = {d0, d0 / 2, d0 / 4, d0 / 8, d0 / 16, d0 / 32, d0 / 64, d0 / 128};
        if (depth == 0) {
            String[][] boom = new String[1][1];
            boom[0][0] = "d0_x0_y0.png";
            cor1 = ROOT_ULLON;
            cor2 = ROOT_LRLON;
            cor3 = ROOT_ULLAT;
            cor4 = ROOT_LRLON;
            return boom;
        }
        double interval = pos[depth];
        double currentlon = ROOT_ULLON;
        while (true) {
            if (ullon < currentlon + interval) {
                uplon++;
                downlon++;
                cor1 = currentlon;
                break;
            }
            currentlon = currentlon + interval;
            uplon++;
            downlon++;
        }
        while (true) {
            if (lrlon < currentlon + interval) {
                cor2 = currentlon + interval;
                break;
            }
            currentlon = currentlon + interval;
            downlon++;
        }
        double ld0 = ROOT_ULLAT - ROOT_LRLAT;
        double[] pos2 = {ld0, ld0 / 2, ld0 / 4, ld0 / 8, ld0 / 16, ld0 / 32, ld0 / 64, ld0 / 128};
        double interval2 = pos2[depth];
        double currentlat = ROOT_ULLAT;
        while (true) {
            if (ullat > currentlat - interval2) {
                uplat++;
                downlat++;
                cor3 = currentlat;
                break;
            }
            uplat++;
            downlat++;
            currentlat = currentlat - interval2;
        }
        while (true) {
            if (lrlat > currentlat - interval2) {
                cor4 = currentlat - interval2;
                break;
            }
            currentlat = currentlat - interval2;
            downlat++;
        }
        int numRows = downlon - uplon + 1;
        int numCols = downlat - uplat + 1;
        String[][] grid = new String[numCols][numRows];
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                String x = "_x" + Integer.toString(uplon + j);
                String y = "_y" + Integer.toString(uplat + i);
                String format = "d" + Integer.toString(depth) + x + y + ".png";
                grid[i][j] = format;
            }
        }

        System.out.println("uplon: " + uplon);
        System.out.println("downlon: " + downlon);
        System.out.println("uplat: " + uplat);
        System.out.println("downlat: " + downlat);
        return grid;
    }
}
