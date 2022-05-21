# server-donations
A spigot plugin to add a donations system to spigot server using Midtrans as the payment gateway

# How to Setup
Sebelum masuk ke langkah langkah setup config, anda harus memiliki:
- Domain
- Additional Open Port

1. Masuk ke dashboard Midtrans dan temukan client dan server key kalian (bebas antara production atau sandbox)
2. Masukkan client dan server key ke `config.yml`
3. Pastikan kalian memiliki open port pada server kalian, lalu masukkan port yang open tersebut di `config.yml`
4. (Optional) Ubah notification path, bagian ini digunakan agar server bisa memiliki notifikasi jika ada payment masuk
5. Buka ke cloudflare di domain anda dan buat A record yang point ke IP server anda, A record yang dibuat akan digunakan di langkah selanjutnya
6. Buka Settings -> Configuration pada dashboard Midtrans
7. Ubah Payment Notification URL menjadi `http://{hostname_that_point_to_your_server-ip}:{port}/{notification-path}` dan pastikan `{notification-path}` sudah sama dengan yang ada di `config.yml`
8. (Optional) Masukkan detail login mysql di `config.yml` untuk menggunakan mysql
9. Selesai
