# EcoLife Weather Widget - Ethiopia

## Overview
EcoLife is a lightweight JavaFX weather widget that displays the **current weather** and a **3-day forecast** for Ethiopian cities.  
It provides a quick glance at temperature, weather conditions, and daily tips related to agriculture, lifestyle, and outdoor activities.

This project was created as part of a GUI/JavaFX challenge, combining **interactive UI elements**, **animations**, and **data-driven updates**.

---

## Company Choice and Justification
I chose to develop this widget under the hypothetical company **EcoLife Solutions** because:

- The company focuses on **eco-friendly solutions and environmental awareness**.
- Weather awareness is critical for **agriculture**, **urban planning**, and **daily activities**, especially in Ethiopia.
- The widget demonstrates how small digital tools can improve **decision-making for farmers and citizens**.

---

## Design Rationale

### **Colors**
- **#2E7D32 (Leaf Green)**: Represents growth, nature, and eco-friendliness.
- **Light backgrounds (#f5f8fc, white)**: Keeps the interface clean and modern.
- **Accent colors for tips and buttons**: Provide visual hierarchy without overwhelming the user.

### **Fonts**
- Default system fonts, combined with bold text for city titles and key metrics (temperature).
- Clear, readable typography for small devices (widget-style UI).

### **Layout**
- **BorderPane**: Main layout to separate Top Bar (City + Search), Center (Weather), and Bottom (Forecast).
- **HBox and VBox**: Organize forecast items, status info, and actions.
- **ComboBox with autocomplete**: Provides intuitive search by first letters.
- **Leaf Ellipse + RotateTransition**: Visual animation to make the widget lively.

---

## Features
1. Search for **Ethiopian cities** with first-letter autocomplete.
2. Display **current temperature**, weather condition, and daily tip.
3. 3-day forecast for quick planning.
4. Animated **leaf icon** for a subtle dynamic effect.
5. Clean, modern, and responsive **JavaFX UI**.

---

## Reflection
**What I learned:**
- Implementing **ComboBox autocomplete** in JavaFX.
- Using **JavaFX animations** to enhance user experience.
- Organizing data in **maps and classes** for clean architecture.
- Styling UI with **external CSS** for consistent look and feel.

**Challenges:**
- Animating the leaf while keeping the layout responsive.
- Filtering city names dynamically as the user types.

**Future improvements:**
- Fetch live weather from an API.
- Add more forecast days and city-specific icons.
- Make the widget resizable and mobile-friendly.


git clone https://github.com/yourusername/EcoLifeWeatherWidget.git
cd EcoLifeWeatherWidget
