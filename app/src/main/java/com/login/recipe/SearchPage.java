package com.login.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

public class SearchPage extends AppCompatActivity {

    private MyApplication app;
    private SearchView searchView;

    // Cuisine radio handling
    private boolean cuisineIsChecked = false;
    private int cuisineCheckedId;

    // Type radio handling
    private boolean typeIsChecked = false;
    private int typeCheckedId;

    //radio buttons cuisine
    private RadioButton meat;
    private RadioButton middle_eastern;
    private RadioButton italian;
    private RadioButton baking;
    private RadioButton asian;
    private RadioButton european;

    //skills radio button
    private RadioButton begginer;
    private RadioButton intermediate;
    private RadioButton pro;

    //radio buttons type
    private RadioButton dessert;
    private RadioButton soup;
    private RadioButton main;
    private RadioButton appetizer;
    private RadioButton salad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //radio buttons cuisine
        meat = findViewById(R.id.meat_b);
        middle_eastern = findViewById(R.id.middle_b);
        italian = findViewById(R.id.italian_b);
        baking = findViewById(R.id.baking_b);
        asian = findViewById(R.id.asian_b);
        european = findViewById(R.id.european_b);

        //radio buttons skill
        begginer = findViewById(R.id.check_beginner);
        intermediate = findViewById(R.id.check_inter);
        pro = findViewById(R.id.check_pro);

        //radio buttons type
        dessert = findViewById(R.id.checkBoxDessert);
        soup = findViewById(R.id.checkBoxSoup);
        main = findViewById(R.id.checkBoxMain);
        appetizer = findViewById(R.id.checkBoxAppetizer);
        salad = findViewById(R.id.checkBoxSalad);

        // cuisine radio group
        final RadioGroup cuisine1 = findViewById(R.id.cuisine_radio_group1);
        final RadioGroup cuisine2 = findViewById(R.id.cuisine_radio_group2);
        cuisine1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && cuisineIsChecked) {
                    cuisineIsChecked = false;
                    cuisine2.clearCheck();
                    cuisineCheckedId = checkedId;
                }
                cuisineIsChecked = true;
                cuisineCheckedId = checkedId;
            }
        });

        cuisine2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && cuisineIsChecked) {
                    cuisineIsChecked = false;
                    cuisine1.clearCheck();
                    cuisineCheckedId = checkedId;
                }
                cuisineIsChecked = true;
                cuisineCheckedId = checkedId;
            }
        });

        // type radio group
        final RadioGroup type1 = findViewById(R.id.type_radio_group1);
        final RadioGroup type2 = findViewById(R.id.type_radio_group2);
        type1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && typeIsChecked) {
                    typeIsChecked = false;
                    type2.clearCheck();
                    typeCheckedId = checkedId;
                }
                typeIsChecked = true;
            }
        });

        type2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && typeIsChecked) {
                    typeIsChecked = false;
                    type1.clearCheck();
                    typeCheckedId = checkedId;
                }
                typeIsChecked = true;
            }
        });

        // skills radio group
        final RadioGroup skills = findViewById(R.id.radio_group_skills);

        app = ((MyApplication)getApplicationContext());
        Button search = findViewById(R.id.search_button);
        searchView = findViewById(R.id.free_text_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                app.setSearchByFreeText(query);
                startActivity(new Intent(SearchPage.this, SearchResults.class));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String freeText = searchView.getQuery().toString();
                if (freeText.equals(""))
                    app.setSearchByFreeText(null);
                else
                    app.setSearchByFreeText(freeText);
                app.setSearchByEmail(null);
                if (skills.getCheckedRadioButtonId() != -1) {
                    if (begginer.isChecked())
                        app.setSearchBySkills(UserProfile.skillLevel.BEGINNER);
                    else if (intermediate.isChecked())
                        app.setSearchBySkills(UserProfile.skillLevel.INTERMEDIATE);
                    else if (pro.isChecked())
                        app.setSearchBySkills(UserProfile.skillLevel.PRO);
                }
                else
                    app.setSearchBySkills(null);
                if (cuisineIsChecked)
                    if(asian.isChecked())
                        app.setSearchByCuisine("asian");
                    else if(middle_eastern.isChecked())
                        app.setSearchByCuisine("middle_eastern");
                    else if(italian.isChecked())
                        app.setSearchByCuisine("italian");
                    else if(european.isChecked())
                        app.setSearchByCuisine("european");
                    else if(baking.isChecked())
                        app.setSearchByCuisine("baking");
                    else if(meat.isChecked())
                        app.setSearchByCuisine("meat");
                else
                    app.setSearchByCuisine(null);

                if (typeIsChecked)
                    if(main.isChecked())
                        app.setSearchByType(Recipe.recipeType.MAIN);
                    else if(dessert.isChecked())
                        app.setSearchByType(Recipe.recipeType.DESSERT);
                    else if(salad.isChecked())
                        app.setSearchByType(Recipe.recipeType.SALAD);
                    else if(soup.isChecked())
                        app.setSearchByType(Recipe.recipeType.SOUP);
                    else if(appetizer.isChecked())
                        app.setSearchByType(Recipe.recipeType.APPETIZER);
                    else
                        app.setSearchByType(null);

                startActivity(new Intent(SearchPage.this, SearchResults.class));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout_button: {
                finish();
                startActivity(new Intent(SearchPage.this, MainActivity.class));
                break;
            }
            case R.id.my_area_button_user_menu: {
                app.setIsMyArea(true);
                startActivity(new Intent(SearchPage.this, MyArea.class));
                break;
            }
            case R.id.home_page_button_user_menu: {
                startActivity(new Intent(SearchPage.this, HomePage.class));
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
