import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Save, ArrowLeft } from 'lucide-react';
import toast from 'react-hot-toast';
import api from '../lib/api.ts';
import Navbar from '../components/Navbar.tsx';
import Editor from '../components/Editor.tsx';
import { formatDistanceToNow } from 'date-fns';

interface Document {
  id: string;
  titre: string;
  contenu: string;
  updatedAt: string;
}

export default function DocumentEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [document, setDocument] = useState<Document | null>(null);
  const [contenu, setContent] = useState('');
  const [titre, setTitle] = useState('');
  const [saving, setSaving] = useState(false);
  const [lastSaved, setLastSaved] = useState<Date | null>(null);
  const [proprietaireId, setProprietaireId] = useState<number | null>(null);

  useEffect(() => {
    fetchDocument();
  }, [id]);

  const fetchDocument = async () => {
    try {
      const response = await api.get(`/documents/${id}`);
      console.log("here"+response.data.proprietaire);
      setDocument(response.data);
      setContent(response.data.contenu || '');
      setTitle(response.data.titre);
      setProprietaireId(response.data.proprietaire.id); // Set the owner ID

    } catch (error) {
      toast.error('Failed to load document');
      navigate('/');
    }
  };

  const handleSave = async () => {
    if (!document) return;
    
    setSaving(true);
    try {

      console.log("here "+titre +" "+contenu);
      await api.put(`/documents/update/${id}`, {
        titre,
        contenu,
        proprietaireId
      });
      setLastSaved(new Date());
      toast.success('Document saved');
    } catch (error) {
      toast.error('Failed to save document');
    } finally {
      setSaving(false);
    }
  };

  const handleTitleChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const newTitle = e.target.value;
    setTitle(newTitle);
    
    try {
      await api.put(`/documents/update/${id}`, {
        titre: newTitle,
        contenu,
        proprietaireId
      });
    } catch (error) {
      console.error('Failed to update title');
    }
  };

  if (!document) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main className="max-w-5xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <div className="mb-6 flex items-center justify-between">
          <div className="flex items-center gap-4">
            <button
              onClick={() => navigate('/')}
              className="inline-flex items-center gap-2 text-gray-600 hover:text-gray-900"
            >
              <ArrowLeft className="h-4 w-4" />
              Back
            </button>
            <input
              type="text"
              value={titre}
              onChange={handleTitleChange}
              className="text-2xl font-bold text-gray-900 bg-transparent border-0 focus:outline-none focus:ring-0 p-0"
              placeholder="Untitled Document"
            />
          </div>
          <div className="flex items-center gap-4">
            {lastSaved && (
              <span className="text-sm text-gray-500">
                Last saved {formatDistanceToNow(lastSaved, { addSuffix: true })}
              </span>
            )}
            <button
              onClick={handleSave}
              disabled={saving}
              className="inline-flex items-center gap-2 px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50"
            >
              <Save className="h-4 w-4" />
              {saving ? 'Saving...' : 'Save'}
            </button>
          </div>
        </div>
        
        <Editor content={contenu} onChange={setContent} />
      </main>
    </div>
  );
}