import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FileText, LogOut } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext.tsx';
import api from '../lib/api.ts';

export default function Navbar() {
  const { logout } = useAuth();
  const { isAuthenticated, user } = useAuth();
  

  return (
    <nav className="bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/" className="flex items-center gap-2">
              <FileText className="h-8 w-8 text-indigo-600" />
              <span className="font-bold text-xl text-gray-900">DocManager</span>
            </Link>
          </div>
          <div className="flex flex-col items-start gap-2">
          <h1 className="text-md mt-2 font-bold text-gray-700">
              {user.prenom} {user.nom}
            </h1>
            <button
              onClick={logout}
              className="inline-flex items-center gap-2 px-10  text-sm font-medium text-red-700 hover:text-gray-900"
            >
              <LogOut className="h-4 w-4" />
              Sign out
            </button>
            
          </div>


          
        </div>
      </div>
    </nav>
  );
}