package com.buyNotes.controller;

import com.buyNotes.model.PorDefecto;
import com.buyNotes.service.PorDefectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unaVez")
@CrossOrigin(origins = "http://localhost:8080/unaVez")
public class PorDefectoController {

    private final PorDefectoService porDefectoService;


    @PostMapping("/inicializarPresets")
    public ResponseEntity<?> registerUser() {
        PorDefecto uno = new PorDefecto();
        List<String> supermercados = new ArrayList<>();
        supermercados.add("Mercadona");
        supermercados.add("Carrefour");
        supermercados.add("Lidl");
        supermercados.add("DIA");
        supermercados.add("Eroski");
        supermercados.add("Alcampo");
        supermercados.add("Aldi");
        supermercados.add("Consum");
        supermercados.add("Ahorramas");
        supermercados.add("Gadis");
        supermercados.add("BonÀrea");
        supermercados.add("BM Supermercados");
        supermercados.add("Lupa");
        supermercados.add("Alimerka");
        supermercados.add("Condis");
        supermercados.add("Froiz");
        supermercados.add("Hipercor / Supercor");
        supermercados.add("Covirán");
        supermercados.add("PrimaPrix");
        supermercados.add("Supermercados MAS / Cash Fresh");
        supermercados.add("Charter");
        supermercados.add("Suma");
        supermercados.add("Deza");
        supermercados.add("Tu Trébol / HiperDino");
        uno.setSupermercadosSugeridos(supermercados);
        List<Productos> productos = new ArrayList<>();
        productos.add(new Productos("Manzana", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Pera", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Platano", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Naranja", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Limon", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Fresa", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Uva", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Melon", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Sandia", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Tomate", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Cebolla", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Ajo", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Patata", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Zanahoria", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Lechuga", Categoria.FRUTAS_Y_VERDURAS));
        productos.add(new Productos("Pimiento", Categoria.FRUTAS_Y_VERDURAS));

        productos.add(new Productos("Pollo entero", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Pechuga de pollo", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Filete de ternera", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Carne picada", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Cinta de lomo", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Costillas de cerdo", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Jamon cocido", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Jamon serrano", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Pavo en lonchas", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Chorizo", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Salchichon", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Lomo embuchado", Categoria.CARNICERIA_Y_CHARCUTERIA));
        productos.add(new Productos("Salchichas", Categoria.CARNICERIA_Y_CHARCUTERIA));

        productos.add(new Productos("Salmon", Categoria.PESCADERIA));
        productos.add(new Productos("Merluza", Categoria.PESCADERIA));
        productos.add(new Productos("Dorada", Categoria.PESCADERIA));
        productos.add(new Productos("Lubina", Categoria.PESCADERIA));
        productos.add(new Productos("Atun", Categoria.PESCADERIA));
        productos.add(new Productos("Bacalao", Categoria.PESCADERIA));
        productos.add(new Productos("Sardina", Categoria.PESCADERIA));
        productos.add(new Productos("Boqueron", Categoria.PESCADERIA));
        productos.add(new Productos("Gambas", Categoria.PESCADERIA));
        productos.add(new Productos("Langostinos", Categoria.PESCADERIA));
        productos.add(new Productos("Calamares", Categoria.PESCADERIA));
        productos.add(new Productos("Sepia", Categoria.PESCADERIA));
        productos.add(new Productos("Mejillones", Categoria.PESCADERIA));

        productos.add(new Productos("Leche entera", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Leche semidesnatada", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Leche desnatada", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Bebida de avena", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Huevos", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Yogur natural", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Yogur de sabores", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Queso fresco", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Queso curado", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Queso en lonchas", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Queso rallado", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Mantequilla", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Margarina", Categoria.LACTEOS_Y_HUEVOS));
        productos.add(new Productos("Nata para cocinar", Categoria.LACTEOS_Y_HUEVOS));

        productos.add(new Productos("Barra de pan", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Pan de molde", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Pan integral", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Pan de hamburguesa", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Croissant", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Ensaimada", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Magdalenas", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Galletas Maria", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Galletas de chocolate", Categoria.PANADERIA_Y_PASTELERIA));
        productos.add(new Productos("Bizcocho", Categoria.PANADERIA_Y_PASTELERIA));

        productos.add(new Productos("Arroz", Categoria.DESPENSA));
        productos.add(new Productos("Macarrones", Categoria.DESPENSA));
        productos.add(new Productos("Espaguetis", Categoria.DESPENSA));
        productos.add(new Productos("Fideos", Categoria.DESPENSA));
        productos.add(new Productos("Lentejas", Categoria.DESPENSA));
        productos.add(new Productos("Garbanzos", Categoria.DESPENSA));
        productos.add(new Productos("Alubias", Categoria.DESPENSA));
        productos.add(new Productos("Aceite de oliva", Categoria.DESPENSA));
        productos.add(new Productos("Aceite de girasol", Categoria.DESPENSA));
        productos.add(new Productos("Vinagre", Categoria.DESPENSA));
        productos.add(new Productos("Sal", Categoria.DESPENSA));
        productos.add(new Productos("Azucar", Categoria.DESPENSA));
        productos.add(new Productos("Harina de trigo", Categoria.DESPENSA));
        productos.add(new Productos("Cafe molido", Categoria.DESPENSA));
        productos.add(new Productos("Cafe en capsulas", Categoria.DESPENSA));
        productos.add(new Productos("Cacao en polvo", Categoria.DESPENSA));
        productos.add(new Productos("Te o Infusiones", Categoria.DESPENSA));
        productos.add(new Productos("Atun en conserva", Categoria.DESPENSA));
        productos.add(new Productos("Sardinillas en lata", Categoria.DESPENSA));
        productos.add(new Productos("Tomate frito", Categoria.DESPENSA));
        productos.add(new Productos("Mayonesa", Categoria.DESPENSA));
        productos.add(new Productos("Ketchup", Categoria.DESPENSA));
        productos.add(new Productos("Aceitunas", Categoria.DESPENSA));
        productos.add(new Productos("Frutos secos", Categoria.DESPENSA));

        productos.add(new Productos("Agua mineral", Categoria.BEBIDAS));
        productos.add(new Productos("Agua con gas", Categoria.BEBIDAS));
        productos.add(new Productos("Refresco de cola", Categoria.BEBIDAS));
        productos.add(new Productos("Refresco de naranja", Categoria.BEBIDAS));
        productos.add(new Productos("Refresco de limon", Categoria.BEBIDAS));
        productos.add(new Productos("Zumo de naranja", Categoria.BEBIDAS));
        productos.add(new Productos("Zumo de melocoton", Categoria.BEBIDAS));
        productos.add(new Productos("Cerveza", Categoria.BEBIDAS));
        productos.add(new Productos("Vino tinto", Categoria.BEBIDAS));
        productos.add(new Productos("Vino blanco", Categoria.BEBIDAS));
        productos.add(new Productos("Ginebra", Categoria.BEBIDAS));
        productos.add(new Productos("Ron", Categoria.BEBIDAS));
        productos.add(new Productos("Whisky", Categoria.BEBIDAS));

        productos.add(new Productos("Guisantes congelados", Categoria.CONGELADOS));
        productos.add(new Productos("Espinacas congeladas", Categoria.CONGELADOS));
        productos.add(new Productos("Salteado de verduras", Categoria.CONGELADOS));
        productos.add(new Productos("Pescado rebozado", Categoria.CONGELADOS));
        productos.add(new Productos("Varitas de merluza", Categoria.CONGELADOS));
        productos.add(new Productos("Croquetas", Categoria.CONGELADOS));
        productos.add(new Productos("Empanadillas", Categoria.CONGELADOS));
        productos.add(new Productos("Patatas fritas para horno", Categoria.CONGELADOS));
        productos.add(new Productos("Pizza congelada", Categoria.CONGELADOS));
        productos.add(new Productos("Helado de vainilla", Categoria.CONGELADOS));
        productos.add(new Productos("Polos de hielo", Categoria.CONGELADOS));

        productos.add(new Productos("Champu", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Acondicionador", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Gel de ducha", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Jabon de manos", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Esponja", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Pasta de dientes", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Cepillo de dientes", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Enjuague bucal", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Desodorante", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Crema hidratante", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Espuma de afeitar", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Cuchillas de afeitar", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Papel higienico", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Compresas", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Tampones", Categoria.CUIDADO_PERSONAL_E_HIGIENE));
        productos.add(new Productos("Bastoncillos para los oidos", Categoria.CUIDADO_PERSONAL_E_HIGIENE));

        productos.add(new Productos("Detergente para la ropa", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Suavizante", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Quitamanchas", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Lavavajillas a mano", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Pastillas para lavavajillas", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Fregasuelos", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Limpiacristales", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Multiusos", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Lejia", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Amoniaco", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Rollo de papel de cocina", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Servilletas de papel", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Bolsas de basura", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Estropajos", Categoria.LIMPIEZA_DEL_HOGAR));
        productos.add(new Productos("Bayetas", Categoria.LIMPIEZA_DEL_HOGAR));

        productos.add(new Productos("Pienso para perros", Categoria.MASCOTAS));
        productos.add(new Productos("Comida humeda para perros", Categoria.MASCOTAS));
        productos.add(new Productos("Snacks para perros", Categoria.MASCOTAS));
        productos.add(new Productos("Pienso para gatos", Categoria.MASCOTAS));
        productos.add(new Productos("Comida humeda para gatos", Categoria.MASCOTAS));
        productos.add(new Productos("Arena para gatos", Categoria.MASCOTAS));
        uno.setProductosSugeridos(productos);

        PorDefectoRepo.save(uno);

        return ResponseEntity.ok().body("Todo ha ido correcto");

    }

    @GetMapping("/obtenerSupermercadosPorDefecto")
    private ResponseEntity<?> obtenerSupermercadosPorDefecto(
            @RequestHeader("Authorization") String authHeader
    ){
        PorDefecto uno = porDefectoService.findTopByOrderByIdAsc();
        return ResponseEntity.ok().body(uno.getSupermercadosSugeridos());

    }
    @GetMapping("/obtenerProductosPorDefecto")
    private ResponseEntity<?> obtenerProductosPorDefecto(
            @RequestHeader("Authorization") String authHeader
    ){
        PorDefecto uno = porDefectoService.findTopByOrderByIdAsc();
        return ResponseEntity.ok().body(uno.getProductosSugeridos());
    }

}
