/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package siguienteajuste;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 *
 * @author JOSHUA
 */
public class SiguienteAjuste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int MemoriaTotal =1500;
        int espaciossinasignar = 0;
        int MemoriaS = MemoriaTotal;
        int ultimaParticionAsignada=0;
        
        System.out.print("Ingrese el número de particiones (el sistema Operativo cuenta como particion)");
        int numParticiones = scanner.nextInt();
        List<Particion> particiones = new ArrayList<>();
        for (int i = 0; i < numParticiones; i++) {
            System.out.print("Ingrese el Tamaño de la Particion " + (i + 1) + ": ");
            int tamanoparticion = scanner.nextInt();
            if (tamanoparticion > MemoriaTotal) {
                System.out.println("El tamaño de la partición no puede ser mayor que la memoria total.");
                return;
            }
            particiones.add(new Particion(i + 1, tamanoparticion));
        }        
        
        List<Proceso> procesos = new ArrayList<>();
        procesos.add(new Proceso("S0",100));
        procesos.add(new Proceso("w", 300));
        procesos.add(new Proceso("e", 200));
        procesos.add(new Proceso("d", 450));
        procesos.add(new Proceso("p", 200));
                
        /*procesos.add(new Proceso("S0", 1));
        procesos.add(new Proceso("JAVA", 70));
        procesos.add(new Proceso("Word", 200));
        procesos.add(new Proceso("PAINT", 10));
        procesos.add(new Proceso("sql", 20));
        procesos.add(new Proceso("A", 250));*/
 
        for( int i=0; i<procesos.size();i++){ 
            Proceso proceso = procesos.get(i); 
            boolean asignado = false;
            
            for (int j=ultimaParticionAsignada; j<particiones.size();j++){
                Particion particion = particiones.get(j);
                if (!particion.ocupada && particion.tamano >= proceso.tamano){
                    particion.ocupada = true;
                    particion.nombreProceso = proceso.nombre;
                    
                    asignado = true;
                    
                    MemoriaS -= proceso.tamano;
                    
                    System.out.print(proceso.nombre +" asignado a la Particion "+particion.id+"\n");
                    
                    ultimaParticionAsignada= j+1;
                    break;
                }
            }
            
            if(!asignado){ //para los que no fueron asignados volver a recorrer las particiones
                for (int j=0; j<particiones.size();j++){
                    Particion particion = particiones.get(j);

                    if (!particion.ocupada && particion.tamano >= proceso.tamano){
                        particion.ocupada = true;
                        particion.nombreProceso = proceso.nombre;
                        asignado = true;

                        MemoriaS -= proceso.tamano;

                        System.out.print(proceso.nombre +" asignado a la Particion "+particion.id+"\n");
                        break;
                    }
                }      
            }
            
            if(!asignado){
                System.out.println(proceso.nombre+" No fuiste asignado a memoria");
                espaciossinasignar ++;
            }
        }
        
        //crear una nueva particion a partir de la memoria disponible final
        if(MemoriaS>0){
            Particion nuevaParticion = new Particion(particiones.size() + 1, MemoriaS);
            particiones.add(nuevaParticion);
            System.out.println("Particion nueva con espacio de: "+MemoriaS);
        }
        
        
        
        System.out.println(" ");
        System.out.println("Memoria total disponible: " + MemoriaS);
         for (Particion particion : particiones) {
            System.out.println("Partición " + particion.id + ": Tamaño=" + particion.tamano + ", Ocupada=" + particion.ocupada + ", Proceso=" + particion.nombreProceso);
        }
        
    }
}
