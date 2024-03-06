package com.example.animanga_universe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.animanga_universe.databinding.ActivityMenuPrincipalBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;

public class MenuPrincipal extends AppCompatActivity {

    ActivityMenuPrincipalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        setContentView(binding.getRoot());
        reemplazarFragment(new HomeFragment());
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.home){
                reemplazarFragment(new HomeFragment());
            }else if(id== R.id.forums){
                reemplazarFragment(new ForumsFragment());

            }else if(id== R.id.buscar){
                reemplazarFragment(new BuscarFragment());

            } else if (id== R.id.listas) {
                reemplazarFragment(new ListasFragment());

            } else if (id==R.id.perfil) {
                reemplazarFragment(new PerfilFragment());

            }

            return true;
        });
        }
        public void reemplazarFragment(Fragment fragment){
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();

        }
    }