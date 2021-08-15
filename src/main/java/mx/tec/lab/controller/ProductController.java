/**
 * 
 */
package mx.tec.lab.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import mx.tec.lab.manager.ProductManager;
import mx.tec.lab.vo.Product;

/**
 * @author Javier Sosa Puga
 *
 */
@RestController
public class ProductController {
	@Resource
	private ProductManager productManager;

	/**
	* returns all products
	*@param no parameters 
	*@return the JSON of all the product
	*/
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productManager.getProducts();
		
		ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(products, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	* returns a specific product with the id
	*@param id
	*@return the specific JSON of the product
	*/
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") String id) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.OK);
		}
		
		return responseEntity;
	}	
	
	/**
	 * Adds the product object
	 * @param product Product
	 * @return status 201 if the product is created or status 400 if the server couldn´t complete the request
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Product> product = productManager.addProducts(newProduct);
		
		if (product.isPresent()) {
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.CREATED);
		}
		
		return responseEntity;
	}
	
	/**
	 * Deletes the Product refered by an id
	 * @param id Product id
	 * @return status 200 if the product is deleted or status 204 if the product is not found
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") String id) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			productManager.deleteProduct(product.get());
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		
		return responseEntity;
	}	
	
	/**
	 * The end point for PUT {url}/products/{id}
	 * @param id Product id
	 * @param modifiedProduct a json containing the info for the modified product
	 * @return status 200 if the product is found and updated or status 204 if the product is not found
	 */
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product modifiedProduct) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			productManager.updateProduct(id, modifiedProduct);
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
}
