/**
*
* @author Abdullah Bilgi - b221210350@sakarya.edu.tr
* @since  05/04/2023
* <p>
* Main s�n�f�, .java uzant�l� bir dosyan�n i�inde ka� tane yorum sat�r� oldu�unu bulur.
* </p>
*/

package odev;

import java.io.*;
import java.util.regex.*;

public class Main {

	public static void main(String[] args)  {
		
			
		int teksatirSayisi,coksatirSayisi,javadocSayisi;
        String satir,fonksiyonBulucu,cokluYorumBulucu,javadocBulucu;
        boolean cokluIcinde, javadocIcinde ;
        String aranacakDosya = args[0];
        
        
        
        System.out.println("\n\nS�n�f: "+aranacakDosya.substring(0,aranacakDosya.length()-5));
        
        try 
        {
        	
        BufferedReader br ;
        FileReader flrdr = new FileReader(aranacakDosya);
        br= new BufferedReader(flrdr);
        
        
        fonksiyonBulucu = cokluYorumBulucu = javadocBulucu = "";
        
        teksatirSayisi = coksatirSayisi = javadocSayisi =0;
        
        cokluIcinde = javadocIcinde = false;

        FileWriter teksatirYazici,coklusatirYazici,javadocYazici ;
        
        
        teksatirYazici= new FileWriter("teksatir.txt"); 
        coklusatirYazici= new FileWriter("coksatir.txt");     
        javadocYazici= new FileWriter("javadoc.txt");
        

        
              
        while ((satir = br.readLine()) != null)
        {

            
            Pattern tekliYorumPattern,cokluYorumBasPattern,
            cokluYorumBitPattern,javadocBasPattern,
            javadocBitPattern,fonksiyonPattern ;
            
            
            Matcher tekiYorumMatcher,cokluYorumBasMatcher,
            cokluYorumBitMatcher,javadocBasMatcher,javadocBitMatcher,
            fonksiyonMatcher ;
            
          // Tekli yorum sat�rlar�n� bulmak i�in regex ifade           
            tekliYorumPattern = Pattern.compile("//(.*)",Pattern.DOTALL);
            tekiYorumMatcher = tekliYorumPattern.matcher(satir);
            
            
         // �oklu yorum sat�rlar�n� bulmak i�in regex ifade           
            cokluYorumBasPattern = Pattern.compile(".*?\\s*/\\*(?!\\*).*");          
            cokluYorumBasMatcher = cokluYorumBasPattern.matcher(satir);           
            cokluYorumBitPattern = Pattern.compile("(.*\\*/\\s*).*");           
            cokluYorumBitMatcher = cokluYorumBitPattern.matcher(satir);
            
                        
         // Javadoclar� bulmak i�in regex ifade           
            javadocBasPattern = Pattern.compile("\\s*/\\*\\*.*");           
            javadocBitPattern = Pattern.compile(".*\\*/\\s*");           
            javadocBasMatcher = javadocBasPattern.matcher(satir);            
            javadocBitMatcher= javadocBitPattern.matcher(satir);
            
                      
            fonksiyonPattern = Pattern.compile("(public|private|protected|static|\\s)+[\\w\\<\\>\\[\\]]+\\s+[\\w]+\\(.*?\\)\\s*\\{");          
            fonksiyonMatcher = fonksiyonPattern.matcher(satir);
            
            // Fonksiyonlar bulunur.
            if (fonksiyonMatcher.find())
            {

                String fonksiyonIsmi = fonksiyonMatcher.group();
                fonksiyonBulucu = fonksiyonIsmi;

            }
            
            
            // �oklu yorum sat�rlar� bulunur.
            if (cokluIcinde)
            {
            	
                if (cokluYorumBitMatcher.find())
                {
                	
                    cokluIcinde = false;
                    
                    if (!cokluYorumBulucu.isEmpty())
                    {
                    	
                    	++coksatirSayisi;                
                    	coklusatirYazici.write("Fonksiyon: " + fonksiyonBulucu + "\n\n" + cokluYorumBulucu+
                        		"\n\n------------------------------\n\n");                        
                        cokluYorumBulucu = "";
                        
                    }
                }
                else
                {
                    cokluYorumBulucu += satir.trim() + "\n";
                }
            }
            else if (cokluYorumBasMatcher.find())
            {
                cokluIcinde = true;                
                cokluYorumBulucu += satir.trim() + "\n";
            }
            
            
            // Tekli yorum sat�rlar� bulunur.                        
            if (tekiYorumMatcher.find())
            {
            	
                String comment;
                comment = tekiYorumMatcher.group().trim();
                
                if (!comment.isEmpty()) {
                	
                    if (fonksiyonBulucu.isEmpty()) ;

                    else {
                    	
                    	++teksatirSayisi;
                        teksatirYazici.write("Fonksiyon: " + fonksiyonBulucu + "\n\n" + comment+
                        		"\n\n------------------------------\n\n");

                    	 }
                						}
             }            
            
            
            // Javadoclar bulunur.                        
            if (javadocIcinde)
            {
            	
                if (javadocBitMatcher.find())
                {
                	
                    javadocIcinde = false;
                    
                    if (!javadocBulucu.isEmpty())
                    {
                    	
                    	++javadocSayisi;                    
                        javadocYazici.write("Fonksiyon: " + fonksiyonBulucu + "\n\n" + javadocBulucu+
                        		"\n\n------------------------------\n\n");                        
                        javadocBulucu = "";
                    }
                }
                
                else
                {               	
                    javadocBulucu += satir.trim() + "\n";
                }
                
            }
            
            else if (javadocBasMatcher.find())
            {
            	
                javadocIcinde = true;
                javadocBulucu += satir.trim() + "\n";
            }
            
            
            
            if (satir.contains("}"))
            {

                if (!fonksiyonBulucu.isEmpty()){
                	
                	System.out.println("\tFonksiyon:" + fonksiyonBulucu +
                			"\n\t\tTek Sat�r Yorum Say�s�: " + teksatirSayisi +
                			"\n\t\tCok Sat�r Yorum Say�s�: " + coksatirSayisi +
                			"\n\t\tJavadoc Yorum Says�:\t" + javadocSayisi +
                			"\n------------------------------------------\n");

                fonksiyonBulucu = "";
                teksatirSayisi = coksatirSayisi = javadocSayisi = 0;
                
                							   }

            }

            
        }

        br.close();
        coklusatirYazici.close();
        teksatirYazici.close();
        javadocYazici.close();
        
        }
        
        
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

			}

}
