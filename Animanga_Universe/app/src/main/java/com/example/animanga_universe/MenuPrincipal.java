package com.example.animanga_universe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.animanga_universe.databinding.ActivityMenuPrincipalBinding;

public class MenuPrincipal extends AppCompatActivity {

    ActivityMenuPrincipalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        setContentView(binding.getRoot());
        reemplazarFragment(new HomeFragment(),false);
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.home){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new HomeFragment(),false);
            }else if(id== R.id.forums){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ForumsFragment(),false);

            }else if(id== R.id.buscar){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new BuscarFragment(),true);

            } else if (id== R.id.listas) {
                binding.switchButton.setVisibility(View.VISIBLE);

                reemplazarFragment(new ListasAnimeFragment(),false);
                binding.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_m_foreground));
                            reemplazarFragment(new ListasMangaFragment(),false);
                        }else{
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_a_foreground));
                            reemplazarFragment(new ListasAnimeFragment(),false);
                        }
                    }
                });
            } else if (id==R.id.perfil) {
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new PerfilFragment(),false);

            }

            return true;
        });
        }
        public void reemplazarFragment(Fragment fragment, boolean buscar){
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            if(!buscar){

            }else{
                fragmentTransaction.replace(R.id.frame_layout,fragment,"BusquedaFragment");
                fragmentTransaction.addToBackStack("BusquedaFragment");
            }

            fragmentTransaction.commit();

        }
    }