<img width="64" src="https://user-images.githubusercontent.com/86880683/226280822-2f2e806a-a6da-4111-a154-c27f4253a09d.png"/>

# NiuMap

<p>使用 Google Map API 繪製地圖 Firebase 儲存資料，顯示目前所在位置，查詢各處室所在和距離，並導航至目的地。
方便讓使用者快速查看宜大校園地圖及目前位置，並提供搜尋功能，讓使用者可以輕鬆查找各處室所在以及距離。
這個APP對於新生或是校外訪客來說會很有幫助，可以快速找到各個地方，節省時間和精力，特別是在緊急情況下。</p>

## 功能
- 未授權GPS會提醒使用者請確認是否已授權GPS權限。
- 地圖上繪製各大樓圖案，讓使用者更容易辨識位置。
- 提供各大樓清單，方便使用者查看和搜尋。
- 提供各處室詳細資訊表，幫助使用者更快速地找到目的地。
- 提供地圖搜尋功能，讓使用者可以輸入大樓或處室名稱來尋找目的地。
- 提供快速導航功能，幫助使用者快速到達目的地。
- 計算相對距離，讓使用者可以更精確地估算到達時間。
- 顯示目前所在位置，讓使用者可以更方便地辨識自己的位置。
<table>
  <tr>
    <th colspan="4"> 
        使用範例
    </th>
  </tr>
  <tr>
    <td colspan="4">
      <img src="https://user-images.githubusercontent.com/86880683/226327607-df0c7e30-b3c3-4705-bf7f-f31b7c34eb74.gif" width=850></img>
    </td>
  </tr>
  <tr>
    <th> 
        地圖首頁
    </th>
    <th> 
        地點查詢
    </th>
    <th> 
        列表清單
    </th>
    <th> 
        自動導航
    </th>
  </tr>
</table>


## 如何使用
1. 下載並安裝此應用程式
2. 開啟應用程式後，地圖會自動顯示宜蘭大學校園
3. 使用者可以點選地圖上的地點或是輸入大樓、處室名稱來尋找目的地
4. 應用程式會顯示目的地的路徑和距離，使用者可以選擇步行或是開車
5. 使用者可以跟隨地圖上的指示導航至目的地

## 如何安裝
1. https://github.com/IdONTKnowCHEK/NiuMap/releases/tag/APK
2. 安裝APK於Android裝置上運行

### clone 此存儲庫：

1. git clone https://github.com/your_username/android-indoor-maps-navigation.git
2. 使用 Android Studio 打開專案並安裝到裝置。

## 配置
1. 為了使用 Google Maps API，您需要將自己的 API 金鑰添加到專案中。請遵循以下步驟：
2. 前往 Google Cloud Console 並創建一個新的專案。
3. 啟用 Google Maps API 並生成 API 金鑰。
4. 於跟目錄建立local.properties檔案並新增：
5. MAPS_API_KEY={YOUR_API_KEY}

