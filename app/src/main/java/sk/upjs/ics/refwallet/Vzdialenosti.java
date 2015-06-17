package sk.upjs.ics.refwallet;

/**
 * Created by Maťo21 on 8. 6. 2015.
 */
    import java.io.BufferedReader;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Arrays;


    public class Vzdialenosti {

        private String[] mesta;
        private int pocet_miest=0;
        private int vzdialenosti[][] = null;

        public Vzdialenosti() {
            super();
        }

        public Vzdialenosti(String cesta) {
            super();
            nacitaj(cesta);
        }

        public int pocetMiest(){
            return pocet_miest;
        }

        public int vratVzdialenost(int a, int b){
            if(this.vzdialenosti!=null)
                return this.vzdialenosti[a][b];
            else return -1;
        }

        public String[] vratVsetkyMesta(){
            return this.mesta;
        }

        public String vratMestoPodlaId(int id){
            return this.mesta[id];
        }


        public void nacitaj(String cesta){

            BufferedReader br = null;
            String line = "";
            String splitBy = ";";

            try {
                int k=0;
                br = new BufferedReader(new FileReader(cesta));
                line = br.readLine();
                this.pocet_miest=Integer.parseInt(line);
                this.vzdialenosti = new int[this.pocet_miest][this.pocet_miest];
                line = br.readLine();
                mesta = line.split(splitBy);
                while ((line = br.readLine()) != null) {


                    String[] vzd = line.split(splitBy);
                    for(int i = 0; i < vzd.length; i++){
                        this.vzdialenosti[k][i]=Integer.parseInt(vzd[i]);
                    }
                    k++;

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
