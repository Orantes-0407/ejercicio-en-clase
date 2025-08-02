package com.negocio.services;

import com.negocio.models.Pedido;
import com.negocio.models.Producto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventarioService {
    private final List<Producto> productos;

    public InventarioService() {
        this.productos = new ArrayList<>();
        inicializarProductos();
    }

    private void inicializarProductos() {
        // Usamos los setters validados en lugar de constructor directo para ejemplo
        agregarProducto(new Producto(1, "Hamburguesa", 15.50, 20));
        agregarProducto(new Producto(2, "Pizza", 25.00, 15));
        agregarProducto(new Producto(3, "Tacos", 8.75, 30));
        agregarProducto(new Producto(4, "Refresco", 3.50, 50));
    }

    // ERROR 8 CORREGIDO: Bucle while mejorado a for-each
    public Producto buscarProductoPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo");
        }

        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }

    // ERROR 9 CORREGIDO: Ahora actualiza el stock correctamente
    public boolean venderProducto(int id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }

        Producto producto = buscarProductoPorId(id);
        if (producto == null) {
            return false;
        }

        try {
            producto.reducirStock(cantidad);
            System.out.printf("Venta realizada: %d unidades de %s%n", cantidad, producto.getNombre());
            return true;
        } catch (IllegalStateException e) {
            System.out.printf("No se pudo realizar la venta: %s%n", e.getMessage());
            return false;
        }
    }

    // ERROR 10 CORREGIDO: Eliminado código duplicado y condición corregida
    public List<Producto> obtenerProductosDisponibles() {

        List<Producto> disponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getStock() > 0) {
                disponibles.add(producto);
            }
        }
        return Collections.unmodifiableList(disponibles);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return Collections.unmodifiableList(productos);
    }

    // Método adicional para agregar productos de forma controlada

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (buscarProductoPorId(producto.getId()) != null) {
            throw new IllegalStateException("Ya existe un producto con este ID");
        }

        productos.add(producto);
    }
    public List<Producto> obtenerTodosLosProductos(String nombre) {

        List<Producto> disponible = new ArrayList<>();
        boolean existe = false;
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                existe = true;
            }
            if (existe) {
                System.out.println("Ya existe un producto con el nombre: " + nombre);
            }
           return productos;
        }



    // Método para reponer stock
    public void reponerStock(int id, int cantidad) {
        Producto producto = buscarProductoPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        producto.aumentarStock(cantidad);
    }

}
    }