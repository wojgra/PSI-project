// import React from 'react';

// const Checkout = () => {
//   return (
//     <div className="sr-root">
//       <div className="sr-main">
//         <section className="container">
//           <div>
//             <h1>Single photo</h1>
//             <h4>Purchase a Pasha original photo</h4>
//             <div className="pasha-image">
//               <img
//                 alt="Random asset from Picsum"
//                 src="https://picsum.photos/280/320?random=4"
//                 width="140"
//                 height="160"
//               />
//             </div>
//           </div>

//           <form action="/api/create-checkout-session" method="POST">
//             <button id="submit" role="link">Buy</button>
//           </form>
//         </section>
//       </div>
//     </div>
//   );
// };

// export default Checkout;


import React, { useEffect, useState } from 'react';

const ProductItem = ({ product }) => (
  <li key={product.id} className="product-item">
    <h2>{product.name}</h2>
    {product.description && <p>{product.description}</p>}
    {product.images && product.images.length > 0 ? (
      <img
        src={product.images[0]}
        alt={product.name}
        width="140"
        height="160"
      />
    ) : (
      <p>Brak zdjęcia</p>
    )}
  </li>
);

const ProductList = ({ products }) => (
  <ul className="product-list">
    {products.map((product) => (
      <ProductItem key={product.id} product={product} />
    ))}
  </ul>
);

const CheckoutForm = ({ selectedProduct }) => (
  <form action="/create-checkout-session" method="POST">
    <input type="hidden" name="productId" value={selectedProduct?.id || ''} />
  </form>
);

const Checkout = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null);

  useEffect(() => {
    async function fetchProducts() {
      try {
        const response = await fetch('/products');
        if (!response.ok) {
          throw new Error('Błąd sieci');
        }
        const data = await response.json();
        setProducts(data || []);
        console.log(products)
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    fetchProducts();
  }, []);

  return (
    <div className="checkout-container">
      <h1>Dostępne produkty</h1>
        <>
        <ProductList products={products} />
        <select onChange={(e) => setSelectedProduct(products.find(p => p.id === e.target.value))}>
          <option value="">Wybierz produkt</option>
          {products.map((product) => (
            <option key={product.id} value={product.id}>{product.name}</option>
          ))}
        </select>
        {selectedProduct && selectedProduct.images && selectedProduct.images.length > 0 ? (
          <img
            src={selectedProduct.images[0]}
            alt={selectedProduct.name}
            width="140"
            height="160"
          />
        ) : (
          <p>Brak zdjęcia</p>
        )}
        <CheckoutForm selectedProduct={selectedProduct} />
      </>
    <form action="/api/create-checkout-session" method="POST">
      <button id="submit" role="link">Buy</button>
    </form>
    </div>
  );
};

export default Checkout;
